package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class CertificateDownloadController {

    @GetMapping("/download-certificate")
    public ResponseEntity<byte[]> downloadCertificate(@RequestParam String userName) throws IOException {
        // Путь к сгенерированному сертификату
        String certificatePath = "certificate_" + userName + ".pdf";

        // Читаем файл в байтовый массив
        File file = new File(certificatePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());

        // Устанавливаем заголовки для загрузки PDF
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                .body(fileContent);
    }
}

