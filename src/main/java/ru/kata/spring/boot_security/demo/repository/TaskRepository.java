package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
