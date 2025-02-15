package ru.kata.spring.boot_security.demo.model;

import ru.kata.spring.boot_security.demo.model.Enum.ResultStatus;

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

    @Column(length = 1000, nullable = false)
    private String code; // Код решения

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultStatus result;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "execution_result", columnDefinition = "TEXT")
    private String executionResult;

    public TaskAttempt() {
        this.submittedAt = LocalDateTime.now(); // Устанавливаем дату отправки по умолчанию
    }

    public String getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(String executionResult) {
        this.executionResult = executionResult;
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

    public ResultStatus getResult() {
        return result;
    }

    public void setResult(ResultStatus result) {
        this.result = result;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}