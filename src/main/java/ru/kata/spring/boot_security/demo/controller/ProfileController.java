package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String showProfile(Model model, Principal principal) {
        String username = principal.getName(); // Получаем имя пользователя из контекста
        User user = userService.findByUsername(username); // Получаем пользователя по имени
        model.addAttribute("user", user); // Добавляем пользователя в модель
        return "profile/view"; // Отправляем на страницу профиля
    }

    @GetMapping("/edit")
    public String showEditForm(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "profile/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("user") User updatedUser,
                                @RequestParam(required = false) String newPassword,
                                @RequestParam(required = false) String confirmPassword,
                                @RequestParam(required = false) MultipartFile photoFile,
                                Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);

        // Обновление имени и email
        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setEmail(updatedUser.getEmail());

        // Обработка изменения пароля
        if (StringUtils.hasText(newPassword)) {
            if (newPassword.equals(confirmPassword)) {
                currentUser.setPassword(passwordEncoder.encode(newPassword));
            } else {
                // Ошибка при несовпадении паролей
                return "redirect:/profile/edit?error=passwordMismatch";
            }
        }

        // Сохранение обновленных данных пользователя
        userService.updateUser(currentUser);
        return "redirect:/dashboard";
    }
}