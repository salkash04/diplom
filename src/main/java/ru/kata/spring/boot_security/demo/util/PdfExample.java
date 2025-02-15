package ru.kata.spring.boot_security.demo.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class PdfExample {
    public static void main(String[] args) throws IOException {
        // Создаем новый документ PDF
        PDDocument document = new PDDocument();

        // Создаем новую страницу
        PDPage page = new PDPage();
        document.addPage(page);

        // Создаем поток для записи контента
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Добавляем текст на страницу
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText("Сертификат успешно сгенерирован!");
        contentStream.endText();

        // Закрываем поток
        contentStream.close();

        // Сохраняем документ в файл
        document.save(new File("certificate.pdf"));

        // Закрываем документ
        document.close();
    }
}

