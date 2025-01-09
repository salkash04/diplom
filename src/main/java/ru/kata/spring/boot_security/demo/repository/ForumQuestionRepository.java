package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.ForumQuestion;

import java.util.List;

@Repository
public interface ForumQuestionRepository extends JpaRepository<ForumQuestion, Long> {
    List<ForumQuestion> findAllByOrderByCreatedAtAsc(); // Сортировка по дате (сначала старые)
    List<ForumQuestion> findAllByOrderByCreatedAtDesc(); // Сортировка по дате (сначала новые)
    List<ForumQuestion> findByTitleContainingOrContentContaining(String title, String content);
}