package ru.kata.spring.boot_security.demo.model;

public class SolutionRequest {
    private String code;
    private Long taskId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}

