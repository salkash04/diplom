package ru.kata.spring.boot_security.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.CodeExecutionService;
import ru.kata.spring.boot_security.demo.service.TaskService;

@RestController
@RequestMapping("/api/compiler")
public class CompilerController {
    private final CodeExecutionService codeExecutionService;
    private final TaskService taskService;

    public CompilerController(CodeExecutionService codeExecutionService, TaskService taskService) {
        this.codeExecutionService = codeExecutionService;
        this.taskService = taskService;
    }

    // Эндпоинт для получения шаблонного кода
    @GetMapping("/template")
    public String getTemplateCode() {
        return codeExecutionService.getTemplateCode();
    }

    // Эндпоинт для выполнения пользовательского кода
    @PostMapping("/run")
    public String runCode(@RequestBody String code) {
        return codeExecutionService.executeCode(code);
    }

    // Эндпоинт для выполнения задачи вычисления квадратного корня
    @PostMapping("/sqrt")
    public String executeSquareRoot(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeSquareRootTask(code);
        try {
            saveTaskAttempt(user.getId(), 1L, code, result); // Используем 1L как ID для задачи sqrt
        } catch (Exception e) {
        }
        return result;
    }

    @PostMapping("/check-divisibility")
    public String executeDivisibility(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeDivisibilityTask(code);
        try {
            saveTaskAttempt(user.getId(), 2L, code, result); // Используем 2L как ID для задачи divisibility
        } catch (Exception e) {
        }
        return result;
    }

    private void saveTaskAttempt(Long userId, Long taskId, String code, String result) {
        boolean isSuccess = result.contains("Тест пройден успешно");
        try {
            taskService.saveTaskAttempt(userId, taskId, code, result, isSuccess);
        } catch (Exception e) {
        }
    }
}


