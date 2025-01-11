package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.model.TaskAttempt;

import java.util.List;
import java.util.Optional;

public interface TaskAttemptRepository extends JpaRepository<TaskAttempt, Long> {
    List<TaskAttempt> findByUserId(Long userId);
    @Query("SELECT ta FROM TaskAttempt ta WHERE ta.userId = :userId")
    List<TaskAttempt> findByUserIdSorted(@Param("userId") Long userId, Sort sort);
    Optional<TaskAttempt> findById(Long attemptId);// Получение попыток по ID пользователя
}
