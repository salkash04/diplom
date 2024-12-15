package ru.kata.spring.boot_security.demo.model.dto;

import java.time.LocalDateTime;

public class ForumQuestionDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String userName; // Имя пользователя, который создал вопрос

    public ForumQuestionDTO(Long id, String title, String content, LocalDateTime createdAt, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.userName = userName;
    }

    // Геттеры и сеттеры
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
}
