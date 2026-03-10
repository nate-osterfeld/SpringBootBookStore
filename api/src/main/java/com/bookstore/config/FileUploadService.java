package com.bookstore.config;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Centralized service for handling file uploads across the application.
 * Eliminates duplicated upload logic in BooksService and AuthorsService
 * by providing a single, reusable method for saving uploaded files to disk.
 */
@Service
public class FileUploadService {

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

        Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", subdirectory, filename);
        Files.createDirectories(uploadPath.getParent());
        imageFile.transferTo(uploadPath.toFile());

        return "/uploads/" + subdirectory + "/" + filename;
    }
}
