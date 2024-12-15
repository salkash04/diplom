package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_attempts")
public class TaskAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId; // ID пользователя

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task; // Связь с задачей

    @Column(nullable = false)
    private String code; // Код решения

    @Column(nullable = false)
    private String result; // Результат выполнения

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    public TaskAttempt() {
        this.submittedAt = LocalDateTime.now(); // Устанавливаем дату отправки по умолчанию
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}