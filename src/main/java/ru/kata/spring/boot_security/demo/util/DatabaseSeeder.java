package ru.kata.spring.boot_security.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DatabaseSeeder {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            insertRoles(connection);
            insertCourses(connection);
            insertModules(connection);  // Убедитесь, что модули вставляются до задач
            insertTasks(connection);
            insertUsers(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertCourses(Connection connection) throws SQLException {
        String sql = "INSERT INTO courses (id, created_at, description, image_url, name) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Вставка курса с id = 1
            statement.setInt(1, 1);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Изучите java с нуля");
            statement.setString(4, "/css/images/Java Сore_16_54_00.jpg");
            statement.setString(5, "Java Basic");
            statement.executeUpdate();

            // Вставка курса с id = 2
            statement.setInt(1, 2);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Изучите фреймворки и базы данных для создания веб-приложений");
            statement.setString(4, "/css/images/cghbyu_15_11_17.png");
            statement.setString(5, "Spring Boot");
            statement.executeUpdate();

            // Вставка курса с id = 3
            statement.setInt(1, 3);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Изучите основные вопросы для успешного прохождения собеседований");
            statement.setString(4, "/css/images/Java pre-project_16_54_34.jpg");
            statement.setString(5, "Подготовка к собеседованиям");
            statement.executeUpdate();

            // Вставка курса с id = 4
            statement.setInt(1, 4);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Решите последние задачи что бы получить сертификат");
            statement.setString(4, "/css/images/1_2.png");
            statement.setString(5, "Итоговый курс - получение сертификата");
            statement.executeUpdate();
        }
    }


    private static void insertModules(Connection connection) throws SQLException {
        String sql = "INSERT INTO modules (id, title) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 1); // id для первого модуля
            statement.setString(2, "Модуль 1. Введение в Java");
            statement.executeUpdate();

            statement.setInt(1, 2); // id для второго модуля
            statement.setString(2, "Модуль 2. Типы данных и операции");
            statement.executeUpdate();

            statement.setInt(1, 3); // id для третьего модуля
            statement.setString(2, "Модуль 3. Условные операторы");
            statement.executeUpdate();

            statement.setInt(1, 4); // id для четвертого модуля
            statement.setString(2, "Модуль 4. Массивы и коллекции");
            statement.executeUpdate();

            statement.setInt(1, 5); // id для пятого модуля
            statement.setString(2, "Модуль 5. Объектно-ориентированное программирование");
            statement.executeUpdate();

            statement.setInt(1, 6); // id для шестого модуля
            statement.setString(2, "Модуль 6. Обработка исключений");
            statement.executeUpdate();

            statement.setInt(1, 7); // id для седьмого модуля
            statement.setString(2, "Модуль 1. Введение в Spring и создание первого приложения");
            statement.executeUpdate();

            statement.setInt(1, 8); // id для восьмого модуля
            statement.setString(2, "Модуль 2. Конфигурация приложения и работа с базами данных");
            statement.executeUpdate();

            statement.setInt(1, 9); // id для девятого модуля
            statement.setString(2, "Модуль 3. Внедрение зависимостей и обработка ошибок");
            statement.executeUpdate();

            statement.setInt(1, 10); // id для десятого модуля
            statement.setString(2, "Модуль 4. Работа с RESTful сервисами");
            statement.executeUpdate();

            statement.setInt(1, 11); // id для одиннадцатого модуля
            statement.setString(2, "Модуль 5. Безопасность и тестирование приложений");
            statement.executeUpdate();

            statement.setInt(1, 12); // id для двенадцатого модуля
            statement.setString(2, "Модуль 1. Поиск максимального элемента");
            statement.executeUpdate();

            statement.setInt(1, 13); // id для тринадцатого модуля
            statement.setString(2, "Модуль 2. Число Фибоначчи");
            statement.executeUpdate();
        }
    }

    private static void insertRoles(Connection connection) throws SQLException {
        String sql = "INSERT INTO roles (id, name) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 1); // id для первой роли
            statement.setString(2, "ROLE_USER");
            statement.executeUpdate();

            statement.setInt(1, 2); // id для второй роли
            statement.setString(2, "ROLE_ADMIN");
            statement.executeUpdate();
        }
    }

    private static void insertUsers(Connection connection) throws SQLException {
        String sql = "INSERT INTO users (email, password, role_id, username) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Вставка администратора
            statement.setString(1, "admin@mail.ru");
            statement.setString(2, "$2a$10$pWd5q9jGSEvHc98wU7JI.OOxRp0vlG2plWbC88XvKATOuT1bpMHFu");
            statement.setInt(3, 2); // ROLE_ADMIN
            statement.setString(4, "admin");

            statement.executeUpdate();

            // Вставка пользователя
            statement.setString(1, "user@mail.ru");
            statement.setString(2, "$2a$10$eFmVNMCGdfKfxhSyMOGT/ugEoc5S1Ap3fKLXY5YD4EqQHAybZ6Kfm");
            statement.setInt(3, 1); // ROLE_USER
            statement.setString(4, "user");

            statement.executeUpdate();
        }
    }


    private static void insertTasks(Connection connection) throws SQLException {
        String sql = "INSERT INTO tasks (id, created_at, description, module_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Вставка задачи с id = 1
            statement.setInt(1, 1);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Что такое Java? Основы языка");
            statement.setInt(4, 1); // ID модуля 1
            statement.executeUpdate();

            // Вставка задачи с id = 2
            statement.setInt(1, 2);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Первая программа на Java");
            statement.setInt(4, 1); // ID модуля 1
            statement.executeUpdate();

            // Вставка задачи с id = 3
            statement.setInt(1, 3);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Примитивные типы данных");
            statement.setInt(4, 2); // ID модуля 2
            statement.executeUpdate();

            // Вставка задачи с id = 4
            statement.setInt(1, 4);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Операции с данными");
            statement.setInt(4, 2); // ID модуля 2
            statement.executeUpdate();

            // Вставка задачи с id = 5
            statement.setInt(1, 5);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Условные операторы (if-else, switch-case)");
            statement.setInt(4, 3); // ID модуля 3
            statement.executeUpdate();

            // Вставка задачи с id = 6
            statement.setInt(1, 6);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Циклы (for, while, do-while)");
            statement.setInt(4, 3); // ID модуля 3
            statement.executeUpdate();

            // Вставка задачи с id = 7
            statement.setInt(1, 7);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Массивы");
            statement.setInt(4, 4); // ID модуля 4
            statement.executeUpdate();

            // Вставка задачи с id = 8
            statement.setInt(1, 8);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Коллекции (ArrayList, HashMap)");
            statement.setInt(4, 5); // ID модуля 5
            statement.executeUpdate();

            // Вставка задачи с id = 9
            statement.setInt(1, 9);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Классы и объекты");
            statement.setInt(4, 6); // ID модуля 6
            statement.executeUpdate();

            // Вставка задачи с id = 10
            statement.setInt(1, 10);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Наследование и полиморфизм");
            statement.setInt(4, 7); // ID модуля 7
            statement.executeUpdate();

            // Вставка задачи с id = 11
            statement.setInt(1, 11);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Обработка исключений");
            statement.setInt(4, 8); // ID модуля 8
            statement.executeUpdate();

            // Вставка задачи с id = 12
            statement.setInt(1, 12);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Поиск максимального элемента");
            statement.setInt(4, 12); // ID модуля 12
            statement.executeUpdate();

            // Вставка задачи с id = 13
            statement.setInt(1, 13);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, "Число Фибоначчи");
            statement.setInt(4, 13); // ID модуля 13
            statement.executeUpdate();
        }
    }
}
