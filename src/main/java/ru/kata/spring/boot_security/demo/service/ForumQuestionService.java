package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.ForumQuestion;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.dto.ForumQuestionDTO;
import ru.kata.spring.boot_security.demo.repository.ForumQuestionRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumQuestionService {

    private final ForumQuestionRepository forumQuestionRepository;
    private final UserRepository userRepository; // Добавьте это

    public ForumQuestionService(ForumQuestionRepository forumQuestionRepository, UserRepository userRepository) {
        this.forumQuestionRepository = forumQuestionRepository;
        this.userRepository = userRepository;
    }

    public Long createQuestion(Long userId, Long taskId, String title, String content) {
        ForumQuestion question = new ForumQuestion(userId, taskId, title, content, LocalDateTime.now());
        forumQuestionRepository.save(question);
        return question.getId();
    }

    public List<ForumQuestionDTO> getAllQuestions() {
        List<ForumQuestion> questions = forumQuestionRepository.findAll();
        return questions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ForumQuestionDTO getQuestionDTOById(Long id) {
        ForumQuestion question = forumQuestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));

        String userName = userRepository.findById(question.getUserId())
                .map(User::getUsername)
                .orElse("Unknown User");

        return new ForumQuestionDTO(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                question.getCreatedAt(),
                userName
        );
    }

    public String getUsernameById(Long userId) {
        return userRepository.findById(userId)
                .map(User::getUsername)
                .orElse("Unknown User");
    }

    private ForumQuestionDTO convertToDto(ForumQuestion question) {
        String userName = userRepository.findById(question.getUserId())
                .map(User::getUsername)
                .orElse("Unknown User");
        return new ForumQuestionDTO(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                question.getCreatedAt(),
                userName
        );
    }
}
