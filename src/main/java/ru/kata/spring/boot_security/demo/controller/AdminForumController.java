package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.dto.AnswerResponseDto;
import ru.kata.spring.boot_security.demo.model.dto.ForumQuestionDTO;
import ru.kata.spring.boot_security.demo.service.ForumAnswerService;
import ru.kata.spring.boot_security.demo.service.ForumQuestionService;

import java.util.List;

@Controller
@RequestMapping("/admin/forum")
public class AdminForumController {

    @Autowired
    private ForumQuestionService forumQuestionService;

    @Autowired
    private ForumAnswerService forumAnswerService;

    @GetMapping("/questions")
    public String getAllQuestions(Model model, @RequestParam(required = false) String sortBy) {
        List<ForumQuestionDTO> questions = forumQuestionService.getAllQuestions(sortBy);
        model.addAttribute("questions", questions);
        return "admin/forum-questions";
    }

    @GetMapping("/questions/{id}")
    public String getQuestion(@PathVariable Long id, Model model) {
        ForumQuestionDTO question = forumQuestionService.getQuestionDTOById(id);
        List<AnswerResponseDto> answers = forumAnswerService.getAnswersByQuestionId(id);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "admin/question-details";
    }

    @PostMapping("/questions/{id}/close")
    public String closeQuestion(@PathVariable Long id) {
        forumQuestionService.closeQuestion(id);
        return "redirect:/admin/forum/questions/" + id;
    }

    @PostMapping("/questions/{id}/open")
    public String openQuestion(@PathVariable Long id) {
        forumQuestionService.openQuestion(id);
        return "redirect:/admin/forum/questions/" + id;
    }

    @GetMapping("/questions/search")
    public String searchQuestions(@RequestParam String keyword, Model model) {
        List<ForumQuestionDTO> questions = forumQuestionService.searchQuestions(keyword);
        model.addAttribute("questions", questions);
        model.addAttribute("keyword", keyword);
        return "admin/search-results";
    }

    @PostMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable Long id) {
        forumQuestionService.deleteQuestion(id);
        return "redirect:/admin/forum/questions";
    }

    @PostMapping("/answers/{id}/delete")
    public String deleteAnswer(@PathVariable Long id) {
        try {
            forumAnswerService.deleteAnswer(id);
            return "redirect:/admin/forum/questions";
        } catch (Exception e) {
            e.printStackTrace();
            // Добавьте сообщение об ошибке в модель, если нужно
            return "redirect:/admin/forum/questions?error=delete_failed";
        }
    }

}

