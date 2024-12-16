package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.dto.AnswerResponseDto;
import ru.kata.spring.boot_security.demo.model.dto.ForumQuestionDTO;
import ru.kata.spring.boot_security.demo.service.ForumAnswerService;
import ru.kata.spring.boot_security.demo.service.ForumQuestionService;

import java.util.List;

@Controller
@RequestMapping("/questions")
public class ForumQuestionController {

    private final ForumQuestionService forumQuestionService;
    private final ForumAnswerService forumAnswerService;

    public ForumQuestionController(ForumQuestionService forumQuestionService, ForumAnswerService forumAnswerService) {
        this.forumQuestionService = forumQuestionService;
        this.forumAnswerService = forumAnswerService;
    }

    @GetMapping
    public String getAllQuestions(Model model) {
        List<ForumQuestionDTO> questionDTOs = forumQuestionService.getAllQuestions();
        model.addAttribute("questions", questionDTOs);
        return "questions/questions";
    }

    // Страница для создания нового вопроса
    @GetMapping("/create")
    public String createQuestionForm() {
        return "questions/createQuestion"; // Страница с формой для создания нового вопроса
    }

    // Создание нового вопроса
    @PostMapping
    public String createQuestion(
            @AuthenticationPrincipal User currentUser,
            @RequestParam Long taskId,
            @ModelAttribute ForumQuestionDTO questionDTO) {
        forumQuestionService.createQuestion(
                currentUser.getId(),
                taskId,
                questionDTO.getTitle(),
                questionDTO.getContent()
        );
        return "redirect:/questions";
    }

    // Получение данных для отображения вопроса
    @GetMapping("/{id}")
    public String getQuestionById(@PathVariable Long id, Model model) {
        ForumQuestionDTO questionDTO = forumQuestionService.getQuestionDTOById(id);
        model.addAttribute("question", questionDTO);
        List<AnswerResponseDto> answerDTOs = forumAnswerService.getAnswersByQuestionId(id);
        model.addAttribute("answers", answerDTOs);
        return "questions/questionDetail";
    }

    @PostMapping("/{id}/answers")
    public String createAnswer(
            @PathVariable Long id,
            @RequestParam String content,
            @RequestParam Long userId,
            @RequestParam(required = false) Long parentId) {  // parentId может быть не обязательно
        forumAnswerService.createAnswer(id, userId, content, parentId);  // В сервис передаем parentId
        return "redirect:/questions/" + id; // Перенаправление на страницу вопроса с добавленным ответом
    }
}

