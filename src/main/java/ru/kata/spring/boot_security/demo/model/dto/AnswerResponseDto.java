package ru.kata.spring.boot_security.demo.model.dto;

import java.time.LocalDateTime;

public class AnswerResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String userName;
    private Long userId;

    public AnswerResponseDto(Long id, String content, LocalDateTime createdAt, String userName, Long userId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.userName = userName;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

