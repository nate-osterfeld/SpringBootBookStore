package com.bookstore.api.authors;

import com.bookstore.api.books.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    @Column(length = 2000)
    public String bio;
    public String authorImageUrl;
    @JsonIgnore // ignore "books" on json return (infinite recursion)
    @OneToMany(mappedBy = "author")
    private List<Book> books;

    public Author(Long id, String name, String bio, String authorImageUrl) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.authorImageUrl = authorImageUrl;
    }

    public Author() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
