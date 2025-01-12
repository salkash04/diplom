package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Enum.QuestionStatus;
import ru.kata.spring.boot_security.demo.model.ForumQuestion;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.dto.ForumQuestionDTO;
import ru.kata.spring.boot_security.demo.repository.ForumAnswerRepository;
import ru.kata.spring.boot_security.demo.repository.ForumQuestionRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumQuestionService {

    private final ForumQuestionRepository forumQuestionRepository;
    private final UserRepository userRepository;
    private final ForumAnswerRepository forumAnswerRepository; // Добавьте это, если у вас еще нет
// Добавьте это

    public ForumQuestionService(ForumQuestionRepository forumQuestionRepository, UserRepository userRepository, ForumAnswerRepository forumAnswerRepository) {
        this.forumQuestionRepository = forumQuestionRepository;
        this.userRepository = userRepository;
        this.forumAnswerRepository = forumAnswerRepository;
    }

    public Long createQuestion(Long userId, Long taskId, String title, String content) {
        ForumQuestion question = new ForumQuestion(userId, taskId, title, content, LocalDateTime.now());
        forumQuestionRepository.save(question);
        return question.getId();
    }

    public List<ForumQuestionDTO> getAllQuestions(String sortBy) {
        if (sortBy == null) {
            System.out.println("Sort by is null. Defaulting to no sorting.");
            return forumQuestionRepository.findAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
        List<ForumQuestion> questions;

        switch (sortBy) {
            case "dateAsc":
                questions = forumQuestionRepository.findAllByOrderByCreatedAtAsc();
                break;
            case "dateDesc":
                questions = forumQuestionRepository.findAllByOrderByCreatedAtDesc();
                break;
            default:
                questions = forumQuestionRepository.findAll(); // Возвращаем все вопросы без сортировки
        }

        return questions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public List<ForumQuestionDTO> searchQuestions(String keyword) {
        List<ForumQuestion> questions = forumQuestionRepository.findByTitleContainingOrContentContaining(keyword, keyword);

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
                userName,
                question.getUserId(), // Передаем userId
                question.getStatus().name() // Передаем статус как строку
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
                userName,
                question.getUserId(), // Передаем userId
                question.getStatus().name() // Передаем статус как строку
        );
    }



    public void closeQuestion(Long questionId) {
        ForumQuestion question = forumQuestionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));
        question.setStatus(QuestionStatus.CLOSED);
        forumQuestionRepository.save(question);
    }

    public void openQuestion(Long questionId) {
        ForumQuestion question = forumQuestionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));
        question.setStatus(QuestionStatus.OPEN);
        forumQuestionRepository.save(question);
    }

    public ForumQuestion getQuestionById(Long id) {
        return forumQuestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));
    }

    @Transactional
    public void deleteQuestion(Long id) {
        // Сначала удаляем все ответы, связанные с этим вопросом
        forumAnswerRepository.deleteByQuestionId(id);

        // Затем удаляем сам вопрос
        forumQuestionRepository.deleteById(id);
    }
}
