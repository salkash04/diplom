package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Module;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}
