package ru.kata.spring.boot_security.demo.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.CertificateService;

import java.time.LocalDate;


@RestController
@RequestMapping("/api")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/generateCertificate")
    public ResponseEntity<FileSystemResource> generateCertificate(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.badRequest().build(); // Если пользователь не авторизован
        }

        // Генерация сертификата
        String certificatePath = certificateService.generateCertificate(user.getUsername(), "Java Course", LocalDate.now().toString());

        // Если сертификат не сгенерирован, вернем ошибку
        if (certificatePath == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Создаем объект для отправки файла
        FileSystemResource file = new FileSystemResource(certificatePath);

        // Возвращаем файл с правильным заголовком для скачивания
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }

}


