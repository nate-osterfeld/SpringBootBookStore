package com.bookstore.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Centralized service for handling file uploads across the application.
 * Eliminates duplicated upload logic in BooksService and AuthorsService
 * by providing a single, reusable method for saving uploaded files to disk.
 */
@Service
public class FileUploadService {

    @Value("${gcp.bucket-name}")
    private String bucketName;

    @Value("${gcp.credentials.location}")
    private String credentialsPath;

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    public String saveToGcp(MultipartFile file, String folder) throws IOException {
        File keyFile = new File(credentialsPath);

        // Check if the file exists before trying to open the stream
        if (!keyFile.exists()) {
            System.err.println("CRITICAL: GCP JSON key not found at: " + credentialsPath);
            throw new IOException("GCP Credentials missing. Upload disabled for this environment.");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new FileInputStream(credentialsPath)
        );

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();

        String fileName = folder + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                // This makes the file public immediately upon upload
//                .setAcl(new ArrayList<>(Collections.singletonList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                .build();

        storage.create(blobInfo, file.getBytes());

        return fileName;
    }

    /**
     * Saves an uploaded file to the specified subdirectory under the uploads folder.
     * Sanitizes the filename, adds a UUID prefix to prevent collisions,
     * and creates directories as needed.
     *
     * @param imageFile    the uploaded file
     * @param subdirectory the subdirectory under "uploads/" (e.g. "books" or "authors")
     * @return the relative URL path to the saved file (e.g. "/uploads/books/uuid_filename.jpg"),
     *         or null if the file is empty
     * @throws IOException if an I/O error occurs during file saving
     */
    public String saveUploadedFile(MultipartFile imageFile, String subdirectory) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null) {
            originalFilename = "image";
        }

        String safeFilename = originalFilename.replaceAll("[^a-zA-Z0-9.\\-_]", "_");
        String filename = UUID.randomUUID() + "_" + safeFilename;

        // Base uploads directory (trusted)
        Path baseUploadDir = Paths.get(System.getProperty("user.dir"), "uploads").normalize().toAbsolutePath();

        // Resolve the subdirectory and filename against the base directory
        Path targetDir = baseUploadDir.resolve(subdirectory).normalize().toAbsolutePath();
        Path uploadPath = targetDir.resolve(filename).normalize().toAbsolutePath();

        // Ensure the final path is still within the base uploads directory to prevent path traversal
        if (!uploadPath.startsWith(baseUploadDir)) {
            throw new IOException("Invalid upload path");
        }

        Files.createDirectories(uploadPath.getParent());
        imageFile.transferTo(uploadPath.toFile());

        return "/uploads/" + subdirectory + "/" + filename;
    }
}
