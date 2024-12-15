package ru.kata.spring.boot_security.demo.model.dto;

import java.time.LocalDateTime;

public class AnswerResponseDto {
    private Long id;
    private String content;
    private Long parentId;
    private LocalDateTime createdAt;
    private String userName;
    private Long userId;

    public AnswerResponseDto(Long id, String content, Long parentId, LocalDateTime createdAt, String userName, Long userId) {
        this.id = id;
        this.content = content;
        this.parentId = parentId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

