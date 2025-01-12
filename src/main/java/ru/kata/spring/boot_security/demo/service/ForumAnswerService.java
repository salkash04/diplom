package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.ForumAnswer;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.dto.AnswerResponseDto;
import ru.kata.spring.boot_security.demo.repository.ForumAnswerRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumAnswerService {

    private final ForumAnswerRepository forumAnswerRepository;
    private final UserRepository userRepository; // Добавьте это

    public ForumAnswerService(ForumAnswerRepository forumAnswerRepository, UserRepository userRepository) {
        this.forumAnswerRepository = forumAnswerRepository;
        this.userRepository = userRepository;
    }

    // Метод для создания нового ответа
    public Long createAnswer(Long questionId, Long userId, String content, Long parentId) {
        ForumAnswer answer = new ForumAnswer(questionId, userId, content, LocalDateTime.now(), parentId);
        forumAnswerRepository.save(answer);
        return answer.getId();
    }

    // Получение всех ответов для вопроса
    public List<AnswerResponseDto> getAnswersByQuestionId(Long questionId) {
        List<ForumAnswer> answers = forumAnswerRepository.findByQuestionId(questionId);
        return answers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AnswerResponseDto convertToDto(ForumAnswer answer) {
        String userName = userRepository.findById(answer.getUserId())
                .map(User::getUsername)
                .orElse("Unknown User");

        return new AnswerResponseDto(
                answer.getId(),
                answer.getContent(),
                answer.getParentId(),
                answer.getCreatedAt(),
                userName,
                answer.getUserId() // Добавьте userId
        );
    }

    // Метод для получения имени пользователя по ID
    public String getUsernameById(Long userId) {
        return userRepository.findById(userId)
                .map(User::getUsername)
                .orElse("Unknown User");
    }

    @Transactional
    public void deleteAnswer(Long id) {
        forumAnswerRepository.deleteById(id);
    }

    public ForumAnswer getAnswerById(Long id) {
        return forumAnswerRepository.findById(id).orElse(null);
    }

}
