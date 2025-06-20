package com.example.demo.post;

import java.time.LocalDateTime;

public class PostResponse {
    public Long id;
    public String title;
    public String content;
    public String author;
    public String category;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public PostResponse() {}
    public PostResponse(Long id, String title, String content, String author, String category, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

}
