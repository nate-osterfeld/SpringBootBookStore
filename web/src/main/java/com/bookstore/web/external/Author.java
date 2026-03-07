package com.bookstore.web.external;

import java.util.List;

public class Author {
    private Long id;
    private String name;
    private String bio;
    private String authorImageUrl;
    private List<Book> books;

    public Author(Long id, String name, String bio, String authorImageUrl, List<Book> books) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.authorImageUrl = authorImageUrl;
        this.books = books;
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
