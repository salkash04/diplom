package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.TaskAttempt;

import java.util.List;

public interface TaskAttemptRepository extends JpaRepository<TaskAttempt, Long> {
    List<TaskAttempt> findByUserId(Long userId); // Получение попыток по ID пользователя
}
