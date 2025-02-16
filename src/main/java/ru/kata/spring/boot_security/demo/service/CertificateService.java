package ru.kata.spring.boot_security.demo.service;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CertificateService {

    @Autowired
    private ResourceLoader resourceLoader;

    public String generateCertificate(String userName, String courseName) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDType0Font boldFont = PDType0Font.load(document, new File("src/main/resources/fonts/Roboto-Bold.ttf"));
            PDType0Font regularFont = PDType0Font.load(document, new File("src/main/resources/fonts/Roboto-Regular.ttf"));

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Рисуем стильную рамку
            drawStylishBorder(contentStream, page);

            // Получаем текущую дату
            String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

            // Добавляем содержимое сертификата
            addCertificateContent(contentStream, page, boldFont, regularFont, userName, courseName, date);

            contentStream.close();

            String certificatePath = "certificate_" + userName + ".pdf";
            document.save(new File(certificatePath));
            document.close();

            System.out.println("Сертификат успешно сгенерирован: " + certificatePath);
            return certificatePath;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void drawStylishBorder(PDPageContentStream contentStream, PDPage page) throws IOException {
        float margin = 50;
        float width = page.getMediaBox().getWidth() - 2 * margin;
        float height = page.getMediaBox().getHeight() - 2 * margin;

        contentStream.setStrokingColor(new Color(0, 51, 153)); // Темно-синий
        contentStream.setLineWidth(3);

        // Внешняя рамка
        contentStream.addRect(margin, margin, width, height);
        contentStream.stroke();
    }

    private void addCertificateContent(PDPageContentStream contentStream, PDPage page, PDType0Font boldFont, PDType0Font regularFont, String userName, String courseName, String date) throws IOException {
        float centerX = page.getMediaBox().getWidth() / 2;
        float centerY = page.getMediaBox().getHeight() / 2;

        // Заголовок
        String title = "СЕРТИФИКАТ";
        float titleWidth = boldFont.getStringWidth(title) / 1000 * 36; // Получаем ширину текста в пунктах
        contentStream.beginText();
        contentStream.setFont(boldFont, 36);
        contentStream.setNonStrokingColor(new Color(0, 51, 153)); // Темно-синий
        contentStream.newLineAtOffset(centerX - titleWidth / 2, centerY + 160);
        contentStream.showText(title);
        contentStream.endText();

        // Подзаголовок
        String subtitle = "Настоящий сертификат подтверждает, что";
        float subtitleWidth = regularFont.getStringWidth(subtitle) / 1000 * 18;
        contentStream.beginText();
        contentStream.setFont(regularFont, 18);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(centerX - subtitleWidth / 2, centerY + 120);
        contentStream.showText(subtitle);
        contentStream.endText();

        // Имя участника
        float userNameWidth = boldFont.getStringWidth(userName) / 1000 * 28;
        contentStream.beginText();
        contentStream.setFont(boldFont, 28);
        contentStream.setNonStrokingColor(new Color(0, 102, 204));
        contentStream.newLineAtOffset(centerX - userNameWidth / 2, centerY + 80);
        contentStream.showText(userName);
        contentStream.endText();

        // Текст перед курсом
        String courseText = "успешно завершил(а) курс";
        float courseTextWidth = regularFont.getStringWidth(courseText) / 1000 * 18;
        contentStream.beginText();
        contentStream.setFont(regularFont, 18);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(centerX - courseTextWidth / 2, centerY + 40);
        contentStream.showText(courseText);
        contentStream.endText();

        // Название курса
        float courseNameWidth = boldFont.getStringWidth("Java Elevate") / 1000 * 24;
        contentStream.beginText();
        contentStream.setFont(boldFont, 24);
        contentStream.setNonStrokingColor(new Color(0, 102, 204));
        contentStream.newLineAtOffset(centerX - courseNameWidth / 2, centerY);
        contentStream.showText("Java Elevate");
        contentStream.endText();

        // Разделительная линия
        float lineWidth = 240;
        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setLineWidth(1);
        contentStream.moveTo(centerX - lineWidth / 2, centerY - 30);
        contentStream.lineTo(centerX + lineWidth / 2, centerY - 30);
        contentStream.stroke();

        // Дата
        String dateText = "Дата: " + date;
        float dateTextWidth = regularFont.getStringWidth(dateText) / 1000 * 16;
        contentStream.beginText();
        contentStream.setFont(regularFont, 16);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(centerX - dateTextWidth / 2, centerY - 60);
        contentStream.showText(dateText);
        contentStream.endText();

    }
}
