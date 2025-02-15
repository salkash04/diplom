package ru.kata.spring.boot_security.demo.service;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


@Service
public class CertificateService {

    @Autowired
    private ResourceLoader resourceLoader;

    public String generateCertificate(String userName, String courseName, String date) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDType0Font boldFont = PDType0Font.load(document, new File("src/main/resources/fonts/Roboto-Italic-VariableFont_wdth,wght.ttf"));
            PDType0Font regularFont = PDType0Font.load(document, new File("src/main/resources/fonts/Roboto-Italic-VariableFont_wdth,wght.ttf"));

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Рисуем стильную рамку
            drawStylishBorder(contentStream, page);

            // Добавляем содержимое сертификата
            addCertificateContent(contentStream, page, boldFont, regularFont, userName, courseName, date);

            // Добавляем логотип
            addLogo(contentStream, page, document);

            // Добавляем водяной знак
            addWatermark(contentStream, page, document);

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
        float margin = 30;
        float width = page.getMediaBox().getWidth() - 2 * margin;
        float height = page.getMediaBox().getHeight() - 2 * margin;

        contentStream.setStrokingColor(new Color(30, 144, 255)); // Золотой цвет
        contentStream.setLineWidth(2);

        // Внешняя рамка
        contentStream.addRect(margin, margin, width, height);

        // Внутренняя рамка
        float innerMargin = margin + 10;
        contentStream.addRect(innerMargin, innerMargin, width - 20, height - 20);

        contentStream.stroke();
    }

    private void addCertificateContent(PDPageContentStream contentStream, PDPage page, PDType0Font boldFont, PDType0Font regularFont, String userName, String courseName, String date) throws IOException {
        float centerX = page.getMediaBox().getWidth() / 2;

        contentStream.beginText();
        contentStream.setFont(boldFont, 36);
        contentStream.setNonStrokingColor(new Color(25, 25, 112)); // Темно-синий цвет
        contentStream.newLineAtOffset(centerX - 150, 700);
        contentStream.showText("СЕРТИФИКАТ");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(regularFont, 18);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(centerX - 200, 650);
        contentStream.showText("Настоящим удостоверяется, что");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(boldFont, 24);
        contentStream.setNonStrokingColor(new Color(139, 69, 19)); // Коричневый цвет
        contentStream.newLineAtOffset(centerX - 100, 600);
        contentStream.showText(userName);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(regularFont, 18);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(centerX - 150, 550);
        contentStream.showText("успешно завершил(а) курс");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(boldFont, 22);
        contentStream.setNonStrokingColor(new Color(30, 144, 255)); // Темно-зеленый цвет
        contentStream.newLineAtOffset(centerX - 100, 500);
        contentStream.showText("Java Elevate");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(regularFont, 16);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(centerX - 75, 450);
        contentStream.showText("Дата выдачи: " + date);
        contentStream.endText();
    }

    private void addLogo(PDPageContentStream contentStream, PDPage page, PDDocument document) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/static/css/images/cghbyu_15_11_17.png");
        try (InputStream inputStream = resource.getInputStream()) {
            PDImageXObject logo = PDImageXObject.createFromByteArray(document, inputStream.readAllBytes(), "logo");
            contentStream.drawImage(logo, page.getMediaBox().getWidth() - 150, page.getMediaBox().getHeight() - 150, 100, 100);
        }
    }

    private void addWatermark(PDPageContentStream contentStream, PDPage page, PDDocument document) throws IOException {
        PDType0Font font = PDType0Font.load(document, new File("src/main/resources/fonts/Roboto-Italic-VariableFont_wdth,wght.ttf"));
        contentStream.setFont(font, 60);
        contentStream.setNonStrokingColor(new Color(220, 220, 220)); // Светло-серый цвет

        contentStream.beginText();
        contentStream.setTextMatrix(Math.cos(Math.toRadians(45)), Math.sin(Math.toRadians(45)), -Math.sin(Math.toRadians(45)), Math.cos(Math.toRadians(45)), 100, 200);
        contentStream.showText("Java Elevate");
        contentStream.endText();
    }
}


