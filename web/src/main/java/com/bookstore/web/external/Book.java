package com.bookstore.web.external;

import java.math.BigDecimal;

public class Book {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private BigDecimal price;
    private Integer quantity;
    private String coverImageUrl;
    private String pdfPath;
    private Long authorId;
    private String authorName;

    public Book(Long id, String title, String description, String genre, BigDecimal price, Integer quantity, String coverImageUrl, String pdfPath, Long authorId, String authorName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.quantity = quantity;
        this.coverImageUrl = coverImageUrl;
        this.pdfPath = pdfPath;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public Book() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
