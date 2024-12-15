package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.impl.MailgunEmailService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailgunEmailService mailgunEmailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Отображение страницы регистрации
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Возвращает HTML-шаблон "register.html"
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            mailgunEmailService.sendRegistrationConfirmation(user.getEmail()); // Отправка подтверждения регистрации
            model.addAttribute("email", user.getEmail()); // Передаем email для подтверждения
            return "email-confirmation"; // Возвращаем страницу с модальным окном
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при регистрации: " + e.getMessage());
            return "register"; // Возвращаемся на страницу регистрации с ошибкой
        }
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
        }
        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы");
        }
        return "login"; // Возвращает HTML-шаблон "login.html"
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/dashboard"; // Перенаправление на защищенную страницу после успешного входа
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Неверный email или пароль");
            return "login"; // Возвращаемся на страницу логина с ошибкой
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при входе: " + e.getMessage());
            return "login"; // Возвращаемся на страницу логина с ошибкой
        }
    }

    @PostMapping("/confirm")
    public String confirmEmail(@RequestParam String confirmationCode, @RequestParam String email, Model model) {
        try {
            if (userService.confirmEmail(email, confirmationCode)) { // Реализуйте логику проверки кода
                return "redirect:/auth/login?confirmed";
            } else {
                model.addAttribute("error", "Неверный код подтверждения.");
                model.addAttribute("email", email);
                return "email-confirmation";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при подтверждении: " + e.getMessage());
            model.addAttribute("email", email);
            return "email-confirmation";
        }
    }

}
