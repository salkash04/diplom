package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.ForumAnswer;

import java.util.List;

@Repository
public interface ForumAnswerRepository extends JpaRepository<ForumAnswer, Long> {

    List<ForumAnswer> findByQuestionId(Long questionId);
}
