package ru.kata.spring.boot_security.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.TaskAttempt;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.CertificateService;
import ru.kata.spring.boot_security.demo.service.CodeExecutionService;
import ru.kata.spring.boot_security.demo.service.TaskCompletionService;
import ru.kata.spring.boot_security.demo.service.TaskService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/compiler")
public class CompilerController {
    private final CodeExecutionService codeExecutionService;
    private final TaskService taskService;
    private final TaskCompletionService taskCompletionService;
    private final CertificateService certificateService;

    public CompilerController(CodeExecutionService codeExecutionService, TaskService taskService, TaskCompletionService taskCompletionService, CertificateService certificateService) {
        this.codeExecutionService = codeExecutionService;
        this.taskService = taskService;
        this.taskCompletionService = taskCompletionService;
        this.certificateService = certificateService;
    }

    @GetMapping("/lastAttempt/{taskId}")
    public String getLastAttemptCode(@PathVariable Long taskId, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        Long userId = user.getId();
        System.out.println("User ID: " + userId + ", Task ID: " + taskId); // Отладочная информация
        TaskAttempt lastAttempt = taskService.getLastAttemptForTask(userId, taskId);
        return lastAttempt != null ? lastAttempt.getCode() : "";
    }

    // Эндпоинт для получения шаблонного кода
    @GetMapping("/template")
    public String getTemplateCode() {
        return codeExecutionService.getTemplateCode();
    }

    @PostMapping("/hello")
    public String executeHelloRoot(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeCode(code);
        try {
            saveTaskAttempt(user.getId(), 1L, code, result); // Используем 1L как ID для задачи sqrt
        } catch (Exception e) {
        }
        return result;
    }

    // Эндпоинт для выполнения задачи вычисления квадратного корня
    @PostMapping("/sqrt")
    public String executeSquareRoot(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeSquareRootTask(code);
        try {
            saveTaskAttempt(user.getId(), 2L, code, result); // Используем 1L как ID для задачи sqrt
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
            saveTaskAttempt(user.getId(), 3L, code, result); // Используем 2L как ID для задачи divisibility
        } catch (Exception e) {
        }
        return result;
    }

    @PostMapping("/check-even-odd")
    public String executeEvenOdd(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeEvenOddTask(code);
        try {
            saveTaskAttempt(user.getId(), 4L, code, result); // Используем 1L как ID для задачи четности
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/check-day-of-week")
    public String executeDayOfWeek(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeDayOfWeekTask(code);
        try {
            saveTaskAttempt(user.getId(), 5L, code, result); // Используем 2L как ID для задачи дня недели
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/for-loop")
    public String executeForLoop(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeForLoopTask(code);
        try {
            saveTaskAttempt(user.getId(), 6L, code, result); // Используем 1L как ID для задачи цикла for
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/while-loop")
    public String executeWhileLoop(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeWhileLoopTask(code);
        try {
            saveTaskAttempt(user.getId(), 7L, code, result); // Используем 2L как ID для задачи цикла while
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/do-while-loop")
    public String executeDoWhileLoop(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeDoWhileLoopTask(code);
        try {
            saveTaskAttempt(user.getId(), 8L, code, result); // Используем 3L как ID для задачи цикла do-while
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/array-sum")
    public String executeArraySum(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeArraySumTask(code);
        try {
            saveTaskAttempt(user.getId(), 9L, code, result); // Используем 1L как ID для задачи суммы массива
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/array-max")
    public String executeArrayMax(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeArrayMaxTask(code);
        try {
            saveTaskAttempt(user.getId(), 10L, code, result); // Используем 2L как ID для задачи максимального элемента массива
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/array-sort")
    public String executeArraySort(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeArraySortTask(code);
        try {
            saveTaskAttempt(user.getId(), 11L, code, result); // Используем 3L как ID для задачи сортировки массива
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/array-list")
    public String executeArrayList(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeArrayListTask(code);
        try {
            saveTaskAttempt(user.getId(), 12L, code, result); // Используем 1L как ID для задачи ArrayList
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/hash-map")
    public String executeHashMap(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeHashMapTask(code);
        try {
            saveTaskAttempt(user.getId(), 13L, code, result); // Используем 2L как ID для задачи HashMap
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/hash-map-entry-set")
    public String executeHashMapEntrySet(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeHashMapEntrySetTask(code);
        try {
            saveTaskAttempt(user.getId(), 14L, code, result); // Используем 3L как ID для задачи entrySet
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/class-object")
    public String executeClassObject(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeClassObjectTask(code);
        try {
            saveTaskAttempt(user.getId(), 15L, code, result); // Используем 1L как ID для задачи классов и объектов
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/inheritance-polymorphism")
    public String executeInheritancePolymorphism(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeInheritancePolymorphismTask(code);
        try {
            saveTaskAttempt(user.getId(), 16L, code, result); // Используем 1L как ID для задачи наследования и полиморфизма
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/exception-handling")
    public String executeExceptionHandling(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }
        String result = codeExecutionService.executeExceptionHandlingTask(code);
        try {
            saveTaskAttempt(user.getId(), 17L, code, result); // Используем 1L как ID для задачи обработки исключений
        } catch (Exception e) {}
        return result;
    }

    @PostMapping("/max-element")
    public String executeMaxElement(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }

        String result = codeExecutionService.executeMaxElement(code);


        try {
            saveTaskAttempt(user.getId(), 12L, code, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    @PostMapping("/fibonacci")
    public String executeFibonacci(@RequestBody String code, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "Error: User is not authenticated";
        }

        String result = codeExecutionService.executeFibonacci(code);


        try {
            saveTaskAttempt(user.getId(), 13L, code, result);  // Предполагаем, что taskId = 13 для этого задания
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("/check-tasks-completed")
    public ResponseEntity<Map<String, Boolean>> checkTasksCompleted(@AuthenticationPrincipal User user) {

        if (user == null) return ResponseEntity.badRequest().build();

        boolean completedMaxElement = taskCompletionService.isTaskCompleted(user.getId(), 12L);
        boolean completedFibonacci = taskCompletionService.isTaskCompleted(user.getId(), 13L);

        Map<String, Boolean> result = new HashMap<>();

        result.put("completed", completedMaxElement && completedFibonacci);

        return ResponseEntity.ok(result);
    }

    private void saveTaskAttempt(Long userId, Long taskId, String code, String result) {
        boolean isSuccess = result.contains("Успешно");
        try {
            taskService.saveTaskAttempt(userId, taskId, code, result, isSuccess);
        } catch (Exception e) {
        }
    }
}


