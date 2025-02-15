package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Enum.ResultStatus;
import ru.kata.spring.boot_security.demo.repository.TaskAttemptRepository;

@Service
public class TaskCompletionService {

    @Autowired
    private TaskAttemptRepository taskAttemptRepository;

    public boolean isTaskCompleted(Long userId, Long taskId) {
        // Проверяем, есть ли запись о завершении задания для данного пользователя
        return taskAttemptRepository.existsByUserIdAndTaskIdAndResult(userId, taskId, ResultStatus.SUCCESS);
    }
}

