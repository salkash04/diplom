package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Получение всех задач
    }

    public void saveTaskAttempt(Long userId, Long taskId, String code, String result) {
        TaskAttempt attempt = new TaskAttempt();
        attempt.setUserId(userId);
        attempt.setTask(taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задача не найдена")));
        attempt.setCode(code);
        attempt.setResult(result);
        attempt.setSubmittedAt(LocalDateTime.now()); // Установите дату отправки

        taskAttemptRepository.save(attempt); // Сохранение попытки выполнения задачи
    }


    public List<TaskAttempt> getUserAttempts(Long userId) {
        return taskAttemptRepository.findByUserId(userId); // Получение всех попыток пользователя
    }
}
