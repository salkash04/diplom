package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.SolutionRequest;
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

    @GetMapping("/user/solutions")
    public String viewSolutions(Model model, @AuthenticationPrincipal User user) {
        List<TaskAttempt> attempts = taskService.getUserAttempts(user.getId());
        model.addAttribute("attempts", attempts);
        return "solutions";
    }

    @GetMapping("/user/solutions/{attemptId}")
    public String showSolution(@PathVariable Long attemptId, Model model, @AuthenticationPrincipal User user) {
        TaskAttempt selectedAttempt = taskService.findAttemptById(attemptId);
        List<TaskAttempt> attempts = taskService.getUserAttempts(user.getId());
        model.addAttribute("attempts", attempts);
        model.addAttribute("selectedAttempt", selectedAttempt);
        return "solutions-history";
    }

    @PostMapping("/module1/lesson/{lessonId}/submit")
    public String submitSolution(@PathVariable Long lessonId,
                                 @RequestBody SolutionRequest request,
                                 @AuthenticationPrincipal User user) throws Exception {
        if (user == null) {
            return "redirect:/login";
        }
        String output = JavaRunner.run(request.getCode());
        boolean isSuccess = determineSuccess(output);
        taskService.saveTaskAttempt(user.getId(), request.getTaskId(), request.getCode(), output, isSuccess);
        return "redirect:/module1/lesson/" + lessonId;
    }



    @GetMapping("/module1/lesson/{lessonId}")
    public String getLesson(@PathVariable Long lessonId, Model model, @AuthenticationPrincipal User user) {
        Long userId = user.getId();
        List<TaskAttempt> attempts = taskService.getUserAttempts(userId);
        model.addAttribute("attempts", attempts);

        TaskAttempt lastAttempt = taskService.getLastAttemptForTask(userId, lessonId);
        model.addAttribute("lastCode", lastAttempt != null ? lastAttempt.getCode() : "");

        boolean taskCompleted = taskService.isTaskCompleted(userId, lessonId);
        model.addAttribute("nextLessonAvailable", taskCompleted);  // если задача решена, доступен следующий урок

        switch (lessonId.intValue()) {
            case 1:
                model.addAttribute("lessonTitle", "Что такое Java? Основы языка");
                model.addAttribute("lessonContent", "Java — это объектно-ориентированный язык программирования...");
                return "lessons/lesson1";
            case 2:
                model.addAttribute("lessonTitle", "Первая программа на Java");
                model.addAttribute("lessonContent", "В этом уроке вы научитесь писать вашу первую программу на Java...");
                return "lessons/lesson2";
            case 3:
                model.addAttribute("lessonTitle", "Примитивные типы данных");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать базовый синтаксис языка Java...");
                return "lessons/lesson3";
            case 4:
                model.addAttribute("lessonTitle", "Операции с данными");
                model.addAttribute("lessonContent", "");
                return "lessons/lesson4";
            case 5:
                model.addAttribute("lessonTitle", "Условные операторы");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать условные операторы языка Java...");
                return "lessons/lesson5";
            case 6:
                model.addAttribute("lessonTitle", "Циклы");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать условные операторы языка Java...");
                return "lessons/lesson6";
            case 7:
                model.addAttribute("lessonTitle", "Массивы");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать массивы языка Java...");
                return "lessons/lesson7";
            case 8:
                model.addAttribute("lessonTitle", "Коллекции (ArrayList, HashMap)");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать коллекции языка Java...");
                return "lessons/lesson8";
            case 9:
                model.addAttribute("lessonTitle", "Классы и объекты");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать классы и объекты языка Java...");
                return "lessons/lesson9";
            case 10:
                model.addAttribute("lessonTitle", "Наследование и полиморфизм");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать наследование и полиморфизм языка Java...");
                return "lessons/lesson10";
            case 11:
                model.addAttribute("lessonTitle", "Обработка исключений");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать обработку исключений языка Java...");
                return "lessons/lesson11";
            default:
                throw new RuntimeException("Урок не найден с ID: " + lessonId);
        }
    }

    @GetMapping("/module2/lesson/{lessonId}")
    public String getLessonModule2(@PathVariable Long lessonId, Model model, @AuthenticationPrincipal User user) {
        Long userId = user.getId();
        List<TaskAttempt> attempts = taskService.getUserAttempts(userId);
        model.addAttribute("attempts", attempts);

        TaskAttempt lastAttempt = taskService.getLastAttemptForTask(userId, lessonId);
        model.addAttribute("lastCode", lastAttempt != null ? lastAttempt.getCode() : "");

        switch (lessonId.intValue()) {
            case 1:
                model.addAttribute("lessonTitle", "Что такое Spring Boot?");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson12";
            case 2:
                model.addAttribute("lessonTitle", "Основные преимущества использования Spring Boot.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson13";
            case 3:
                model.addAttribute("lessonTitle", "Установка и настройка окружения для разработки.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson14";
            case 4:
                model.addAttribute("lessonTitle", "Создание простого RESTful API.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson15";
            case 5:
                model.addAttribute("lessonTitle", "Запуск приложения и работа с встроенным сервером (Tomcat).");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson16";
            case 6:
                model.addAttribute("lessonTitle", "Работа с файлами конфигурации (application.properties и application.yml).");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson17";
            case 7:
                model.addAttribute("lessonTitle", "Введение в JPA и Hibernate.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson18";
            case 8:
                model.addAttribute("lessonTitle", "Конфигурация подключения к базе данных.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson19";
            case 9:
                model.addAttribute("lessonTitle", "Создание сущностей и репозиториев с использованием Spring Data JPA.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson20";
            case 10:
                model.addAttribute("lessonTitle", "Основы внедрения зависимостей в Spring.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson21";
            case 11:
                model.addAttribute("lessonTitle", "Использование аннотаций @Autowired, @Component, @Service, @Repository.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson22";
            case 12:
                model.addAttribute("lessonTitle", "Глобальная обработка ошибок с использованием @ControllerAdvice.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson23";
            case 13:
                model.addAttribute("lessonTitle", "Создание контроллеров с использованием аннотации @RestController.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson24";
            case 14:
                model.addAttribute("lessonTitle", "Обработка HTTP-запросов (GET, POST, PUT, DELETE).");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson25";
            case 15:
                model.addAttribute("lessonTitle", "Управление кодами ответа HTTP.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson26";
            case 16:
                model.addAttribute("lessonTitle", "Введение в Spring Security: настройка аутентификации и авторизации.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson27";
            case 17:
                model.addAttribute("lessonTitle", "Написание юнит-тестов с использованием JUnit и Mockito.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson28";
            case 18:
                model.addAttribute("lessonTitle", "Интеграционное тестирование с использованием Spring Test.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson29";
            default:
                throw new RuntimeException("Урок не найден с ID: " + lessonId);
        }
    }

    @GetMapping("/module3/lesson/{lessonId}")
    public String getLessonModule3(@PathVariable Long lessonId, Model model, @AuthenticationPrincipal User user) {
        Long userId = user.getId();
        List<TaskAttempt> attempts = taskService.getUserAttempts(userId);
        model.addAttribute("attempts", attempts);

        TaskAttempt lastAttempt = taskService.getLastAttemptForTask(userId, lessonId);
        model.addAttribute("lastCode", lastAttempt != null ? lastAttempt.getCode() : "");

        switch (lessonId.intValue()) {
            case 1:
                return "interviews-modules/Core-1";
            case 2:
                return "interviews-modules/Core-2";
            case 3:
                return "interviews-modules/Multithreading";
            case 4:
                return "interviews-modules/SQL";
            case 5:
                return "interviews-modules/Hibernate";
            case 6:
                return "interviews-modules/Spring";
            case 7:
                return "interviews-modules/Паттерны";
            case 8:
                return "interviews-modules/Алгоритмы";
            default:
                throw new RuntimeException("Урок не найден с ID: " + lessonId);
        }
    }

    @GetMapping("/module4/lesson/{lessonId}")
    public String getLessonModule4(@PathVariable Long lessonId, Model model, @AuthenticationPrincipal User user) {
        Long userId = user.getId();
        List<TaskAttempt> attempts = taskService.getUserAttempts(userId);
        model.addAttribute("attempts", attempts);

        TaskAttempt lastAttempt = taskService.getLastAttemptForTask(userId, lessonId);
        model.addAttribute("lastCode", lastAttempt != null ? lastAttempt.getCode() : "");

        switch (lessonId.intValue()) {
            case 1:
                model.addAttribute("lessonTitle", "Что такое Spring Boot?");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson30";
            case 2:
                model.addAttribute("lessonTitle", "Основные преимущества использования Spring Boot.");
                model.addAttribute("lessonContent", "В этом модуле мы будем изучать многопоточность языка Java...");
                return "lessons/lesson31";
            default:
                throw new RuntimeException("Урок не найден с ID: " + lessonId);
        }
    }

    private boolean determineSuccess(String output) {
        return output.contains("Успешно: Вывод соответствует ожидаемому результату");
    }
}
