package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.CodeExecutionService;

@RestController
@RequestMapping("/api/compiler")
public class CompilerController {
    private final CodeExecutionService codeExecutionService;

    public CompilerController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
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
    public String executeSquareRoot(@RequestBody String code) {
        return codeExecutionService.executeSquareRootTask(code);
    }

    // Эндпоинт для выполнения задачи проверки делимости на 3
    @PostMapping("/check-divisibility")
    public String executeDivisibility(@RequestBody String code) {
        return codeExecutionService.executeDivisibilityTask(code);
    }
}

