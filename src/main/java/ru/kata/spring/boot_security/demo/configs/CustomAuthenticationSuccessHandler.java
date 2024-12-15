package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.impl.MailgunEmailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MailgunEmailService mailgunEmailService;

    public CustomAuthenticationSuccessHandler(MailgunEmailService mailgunEmailService) {
        this.mailgunEmailService = mailgunEmailService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Получить email пользователя
        String userEmail = authentication.getName();

        // Отправить сообщение через Mailgun
        mailgunEmailService.sendRegistrationConfirmation(userEmail);

        // Перенаправление после успешного входа
        response.sendRedirect("/dashboard");
    }
}

