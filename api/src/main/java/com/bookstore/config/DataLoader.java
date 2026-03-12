package com.bookstore.config;

import com.bookstore.auth.IUsersRepository;
import com.bookstore.auth.User;
import com.bookstore.authors.Author;
import com.bookstore.authors.IAuthorsRepository;
import com.bookstore.books.Book;
import com.bookstore.books.IBooksRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {
    private final IAuthorsRepository authorsRepository;
    private final IBooksRepository booksRepository;
    private final IUsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(
        IAuthorsRepository authorsRepository,
        IBooksRepository booksRepository,
        IUsersRepository usersRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.authorsRepository = authorsRepository;
        this.booksRepository = booksRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        var author1 = new Author(
            null,
            "J.R.R. Tolkien",
            "John Ronald Reuel Tolkien (1892–1973) was an English writer, philologist, and Oxford professor best known for creating the richly imagined Middle-earth legendarium, including The Hobbit and The Lord of the Rings, works that profoundly shaped modern fantasy literature.",
            "https://collectionimages.npg.org.uk/large/mw56725/JRR-Tolkien.jpg"
        );

        var author2 = new Author(
                null,
                "Harper Lee",
                "Harper Lee (1926–2016) was an American novelist whose Pulitzer Prize–winning book To Kill a Mockingbird became one of the most influential works in American literature, exploring themes of justice, morality, and racial inequality.",
                "https://upload.wikimedia.org/wikipedia/commons/b/b5/Photo_portrait_of_Harper_Lee_%28To_Kill_a_Mockingbird_dust_jacket%2C_1960%29.jpg"
        );

        var author3 = new Author(
                null,
                "George Orwell",
                "George Orwell (1903–1950), born Eric Arthur Blair, was an English novelist, critic, and political essayist known for his sharp insight into authoritarianism, social injustice, and political manipulation, expressed in classics like 1984 and Animal Farm.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/George_Orwell_press_photo.jpg/960px-George_Orwell_press_photo.jpg"
        );

        authorsRepository.save(author1);
        authorsRepository.save(author2);
        authorsRepository.save(author3);

        var book1 = new Book(
                null,
                "The Hobbit", author1,
                "A reluctant hobbit named Bilbo Baggins is swept into an adventurous quest to help dwarves reclaim their stolen homeland.",
                "Fantasy",
                new BigDecimal("14.99"),
                3,
                "https://m.media-amazon.com/images/I/712cDO7d73L._AC_UF1000,1000_QL80_.jpg"
        );

        var book2 = new Book(
                null, "To Kill a Mockingbird", author2, "A young girl in the racially divided American South witnesses injustice and compassion as her father defends an innocent Black man.",
                "Classic",
                new BigDecimal("12.50"),
                5,
                "https://m.media-amazon.com/images/I/51lVvSDLQJL._AC_UF1000,1000_QL80_.jpg"
        );

        var book3 = new Book(
                null,
                "1984",
                author3,
                "In a dystopian surveillance state, one man struggles against a totalitarian regime that controls truth and individuality.",
                "Dystopian",
                new BigDecimal("15.00"), 8, "https://www.magicmurals.com/media/catalog/product/cache/af1e2a1566fa2dbb552605e8822354b7/a/d/adg-0000001048_1.jpg"
        );

        booksRepository.save(book1);
        booksRepository.save(book2);
        booksRepository.save(book3);

        var user1 = new User(
            null,
            "admin",
            passwordEncoder.encode("password"),
            "ADMIN"
        );

        var user2 = new User(
            null,
            "user2",
            passwordEncoder.encode("password"),
            "USER"
        );

        var user3 = new User(
            null,
            "user3",
            passwordEncoder.encode("password"),
            "USER"
        );

        usersRepository.save(user1);
        usersRepository.save(user2);
        usersRepository.save(user3);
    }
}
