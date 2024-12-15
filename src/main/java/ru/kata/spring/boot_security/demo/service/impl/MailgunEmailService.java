package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MailgunEmailService {

    private static final String MAILGUN_API_URL = "https://api.mailgun.net/v3/sandboxc812cfe7614c4712b35c1d372affc5f3.mailgun.org/messages";
    private static final String MAILGUN_API_KEY = "7f32df51c1f86c8803439c8ce2dc93fd-da554c25-7240734d";

    private final RestTemplate restTemplate;

    public MailgunEmailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendRegistrationConfirmation(String toEmail) {
        // Создаем заголовки
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("api", MAILGUN_API_KEY);

        // Тело запроса
        String requestBody = "from=no-reply@your-domain.com" +
                "&to=" + toEmail +
                "&subject=Подтверждение регистрации" +
                "&text=Спасибо за регистрацию в нашем сервисе!";

        // Создаем запрос
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Отправляем запрос в Mailgun API
        ResponseEntity<String> response = restTemplate.exchange(
                MAILGUN_API_URL,
                HttpMethod.POST,
                request,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Email успешно отправлен!");
        } else {
            System.err.println("Ошибка при отправке письма: " + response.getBody());
        }
    }
}
