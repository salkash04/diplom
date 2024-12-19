package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Enum.ResultStatus;
import ru.kata.spring.boot_security.demo.model.Task;
import ru.kata.spring.boot_security.demo.model.TaskAttempt;
import ru.kata.spring.boot_security.demo.repository.TaskAttemptRepository;
import ru.kata.spring.boot_security.demo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskAttemptRepository taskAttemptRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskAttemptRepository taskAttemptRepository) {
        this.taskRepository = taskRepository;
        this.taskAttemptRepository = taskAttemptRepository;
    }

    @Transactional
    public void saveTaskAttempt(Long userId, Long taskId, String code, String executionResult, boolean isSuccess) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задача не найдена с ID: " + taskId));

        TaskAttempt attempt = new TaskAttempt();
        attempt.setUserId(userId);
        attempt.setTask(task);
        attempt.setCode(code);
        attempt.setResult(isSuccess ? ResultStatus.SUCCESS : ResultStatus.FAIL);
        attempt.setExecutionResult(executionResult);
        attempt.setSubmittedAt(LocalDateTime.now());

        taskAttemptRepository.save(attempt);
    }

    public List<TaskAttempt> getUserAttempts(Long userId) {
        return taskAttemptRepository.findByUserId(userId);
    }

    public TaskAttempt getLastAttemptForTask(Long userId, Long taskId) {
        List<TaskAttempt> attempts = taskAttemptRepository.findByUserId(userId);
        return attempts.stream()
                .filter(attempt -> attempt.getTask().getId().equals(taskId))
                .findFirst()
                .orElse(null);
    }

}
