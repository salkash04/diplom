package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.TaskAttempt;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.TaskService;
import ru.kata.spring.boot_security.demo.util.JavaRunner;

import java.util.List;

@Controller
public class LessonController {

    private final TaskService taskService;

    @Autowired
    public LessonController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/module1/lesson/{lessonId}")
    public String getLesson(@PathVariable int lessonId, Model model, User user) {
        Long userId = user.getId();
        List<TaskAttempt> attempts = taskService.getUserAttempts(userId);
        model.addAttribute("attempts", attempts);

        switch (lessonId) {
            case 1:
                model.addAttribute("lessonTitle", "Что такое Java? Основы языка");
                model.addAttribute("lessonContent", "Java — это объектно-ориентированный язык программирования...");
                return "lessons/lesson1"; // Возвращаем шаблон для первого урока
            case 2:
                model.addAttribute("lessonTitle", "Первая программа на Java");
                model.addAttribute("lessonContent", "В этом уроке вы научитесь писать вашу первую программу на Java...");
                return "lessons/lesson2"; // Возвращаем шаблон для второго урока
            case 3:
                model.addAttribute("lessonTitle", "Примитивные типы данных");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать базовый синтаксис языка Java...");
                return "lessons/lesson3"; // Возвращаем шаблон для второго урока
             case 4:
                model.addAttribute("lessonTitle", "Операции с данными");
                model.addAttribute("lessonContent", "");
                return "lessons/lesson4"; // Возвращаем шаблон для второго урока
            // Добавьте другие уроки по аналогии...
            default:
                throw new RuntimeException("Урок не найден с ID: " + lessonId);
        }
    }

    @GetMapping("/user/solutions")
    public String viewSolutions(Model model, @AuthenticationPrincipal User user) {
        List<TaskAttempt> attempts = taskService.getUserAttempts(user.getId());
        model.addAttribute("attempts", attempts);
        return "solutions";
    }

    @PostMapping("/module1/lesson/{lessonId}/submit")
    public String submitSolution(@PathVariable Long lessonId,
                                 @RequestParam String code,
                                 @AuthenticationPrincipal User user) throws Exception {
        if (user == null) {
            return "redirect:/login";
        }
        String output = JavaRunner.run(code);
        taskService.saveTaskAttempt(user.getId(), lessonId, code, output);
        return "redirect:/module1/lesson/" + lessonId;
    }
}
