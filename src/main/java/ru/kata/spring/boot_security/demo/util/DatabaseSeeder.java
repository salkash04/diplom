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
            insertCourses(connection);
            insertModules(connection);
            insertTasks(connection);
            insertRoles(connection);
            insertUsers(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertCourses(Connection connection) throws SQLException {
        String sql = "INSERT INTO courses (created_at, description, image_url, name) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Изучите java с нуля");
            statement.setString(3, "/css/images/Java_Сore_16_54_00.jpg");
            statement.setString(4, "Java Basic");
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Изучите фреймворки и базы данных для создания веб-приложений");
            statement.setString(3, "/css/images/cghbyu_15_11_17.png");
            statement.setString(4, "Spring Boot");
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Изучите основные вопросы для успешного прохождения собеседований");
            statement.setString(3, "/css/images/Java_pre-project_16_54_34.jpg");
            statement.setString(4, "Подготовка к собеседованиям");
            statement.executeUpdate();
        }
    }


    private static void insertModules(Connection connection) throws SQLException {
        String sql = "INSERT INTO modules (title) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Модуль 1. Введение в Java");
            statement.executeUpdate();

            statement.setString(1, "Модуль 2. Типы данных и операции");
            statement.executeUpdate();

            statement.setString(1, "Модуль 3. Условные операторы");
            statement.executeUpdate();

            statement.setString(1, "Модуль 4. Массивы и коллекции");
            statement.executeUpdate();

            statement.setString(1, "Модуль 5. Объектно-ориентированное программирование");
            statement.executeUpdate();

            statement.setString(1, "Модуль 6. Обработка исключений");
            statement.executeUpdate();

            statement.setString(1, "Модуль 1. Введение в Spring и создание первого приложения");
            statement.executeUpdate();

            statement.setString(1, "Модуль 2. Конфигурация приложения и работа с базами данных");
            statement.executeUpdate();

            statement.setString(1, "Модуль 3. Внедрение зависимостей и обработка ошибок");
            statement.executeUpdate();

            statement.setString(1, "Модуль 4. Работа с RESTful сервисами");
            statement.executeUpdate();

            statement.setString(1, "Модуль 5. Безопасность и тестирование приложений");
            statement.executeUpdate();
        }
    }

    private static void insertRoles(Connection connection) throws SQLException {
        String sql = "INSERT INTO roles (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "ROLE_USER");
            statement.executeUpdate();
            statement.setString(1, "ROLE_ADMIN");
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
        String sql = "INSERT INTO tasks (created_at, description, module_id) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Что такое Java? Основы языка");
            statement.setInt(3, 1); // ID модуля 1
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Первая программа на Java");
            statement.setInt(3, 1); // ID модуля 1
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Примитивные типы данных");
            statement.setInt(3, 2); // ID модуля 2
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Операции с данными");
            statement.setInt(3, 2); // ID модуля 2
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Условные операторы (if-else, switch-case)");
            statement.setInt(3, 3); // ID модуля 3
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Циклы (for, while, do-while)");
            statement.setInt(3, 3); // ID модуля 3
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Массивы");
            statement.setInt(3, 4); // ID модуля 4
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Коллекции (ArrayList, HashMap)");
            statement.setInt(3, 5); // ID модуля 5
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Классы и объекты");
            statement.setInt(3, 6); // ID модуля 6
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Наследование и полиморфизм");
            statement.setInt(3, 7); // ID модуля 7
            statement.executeUpdate();

            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, "Обработка исключений");
            statement.setInt(3, 8); // ID модуля 8
            statement.executeUpdate();
        }
    }
}
