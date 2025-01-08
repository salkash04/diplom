package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.util.JavaRunner;

@Service
public class CodeExecutionService {

    // Шаблонный код, который будет предоставляться пользователю
    private static final String TEMPLATE_CODE =
            "public class Main {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        // Напишите ваш код здесь\n" +
                    "    }\n" +
                    "}";

    public String getTemplateCode() {
        return TEMPLATE_CODE;
    }

    public String executeCode(String code) {
        try {
            String output = JavaRunner.run(code);
            // Проверяем, соответствует ли вывод ожидаемому результату
            if (output.trim().equals("Hello, World!")) {
                return "Успешно: Вывод соответствует ожидаемому результату!";
            } else {
                return "Ошибка: Ожидался вывод 'Hello, World!', но получен: " + output;
            }
        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeSquareRootTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.contains("Квадратный корень числа") && output.contains("равен")) {
                return "Успешно: Вывод соответствует ожидаемому результату для вычисления квадратного корня!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;
        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeDivisibilityTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.contains("делится на 3")) {
                return "Успешно: Вывод соответствует ожидаемому результату для проверки делимости на 3!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;
        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    //task if/else
    public String executeEvenOddTask(String code) {
        try {
            String output = JavaRunner.run(code);
            if (output.contains("Число четное") || output.contains("Число нечетное")) {
                return "Успешно: Вывод соответствует ожидаемому результату для проверки четности!";
            }
            return "Ошибка: Получен неожиданный вывод: " + output;
        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    //task switch case
    public String executeDayOfWeekTask(String code) {
        try {
            String output = JavaRunner.run(code);
            if (output.contains("Понедельник") || output.contains("Вторник") ||
                    output.contains("Среда") || output.contains("Четверг") ||
                    output.contains("Пятница") || output.contains("Суббота") ||
                    output.contains("Воскресенье")) {
                return "Успешно: Вывод соответствует ожидаемому результату для определения дня недели!";
            }
            return "Ошибка: Получен неожиданный вывод: " + output;
        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeForLoopTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().equals("1\n2\n3\n4\n5\n6\n7\n8\n9\n10")) { // Ожидаемый вывод
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeWhileLoopTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().equals("1\n2\n3\n4\n5\n6\n7\n8\n9\n10")) { // Ожидаемый вывод
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeDoWhileLoopTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().equals("1\n2\n3\n4\n5\n6\n7\n8\n9\n10")) { // Ожидаемый вывод
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeArraySumTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().equals("Сумма элементов массива: 27")) { // Ожидаемый вывод (пример)
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeArrayMaxTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().equals("Максимальный элемент массива: 5")) { // Ожидаемый вывод (пример)
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeArraySortTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().equals("Отсортированный массив: [1, 2, 3, 4, 5]")) { // Ожидаемый вывод (пример)
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeArrayListTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().equals("Сумма элементов массива: 15")) { // Ожидаемый вывод (пример)
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeHashMapTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().equals("Ключи: [apple, banana]")) { // Ожидаемый вывод (пример)
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeHashMapEntrySetTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().contains("-->")) { // Ожидаемый вывод должен содержать пары ключ-значение
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeClassObjectTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().equals("Имя: John, Возраст: 30")) { // Ожидаемый вывод (пример)
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeInheritancePolymorphismTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().equals("Животное издает звук\nГав\nМяу")) { // Ожидаемый вывод (пример)
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

    public String executeExceptionHandlingTask(String code) {
        try {
            String output = JavaRunner.run(code);

            if (output.trim().contains("Ошибка: Деление на ноль!")) { // Ожидаемый вывод (пример)
                return "Успешно: Вывод соответствует ожидаемому результату!";
            }

            return "Ошибка: Получен неожиданный вывод: " + output;

        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }

}


