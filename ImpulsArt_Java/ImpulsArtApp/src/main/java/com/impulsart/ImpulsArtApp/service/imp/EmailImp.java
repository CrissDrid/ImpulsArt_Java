package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailImp implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarCorreo(String destinatario, String asunto, String nombre, String mensaje) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Configura el destinatario y el asunto
            helper.setTo(destinatario);
            helper.setSubject(asunto);

            // Leer el archivo de plantilla y reemplazar los marcadores de posición
            String htmlTemplate = readTemplate("EmailPrueba.html");
            String htmlContent = htmlTemplate
                    .replace("[[nombre]]", nombre)
                    .replace("[[mensaje]]", mensaje);

            // Configura el contenido HTML del correo
            helper.setText(htmlContent, true); // true indica que el contenido es HTML

            // Adjuntar una imagen desde el directorio de recursos
            ClassPathResource imageFile = new ClassPathResource("imagen/logo_impulsart.jpg");
            if (imageFile.exists()) {
                helper.addInline("imagen", imageFile);
            }

            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    @Override
    public void enviarCorreoSubasta(String destinatario, String asunto, String nombre, String mensaje) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Configura el destinatario y el asunto
            helper.setTo(destinatario);
            helper.setSubject(asunto);

            // Leer el archivo de plantilla y reemplazar los marcadores de posición
            String htmlTemplate = readTemplate("EmailSubasta.html");
            String htmlContent = htmlTemplate
                    .replace("[[nombre]]", nombre)
                    .replace("[[mensaje]]", mensaje);

            // Configura el contenido HTML del correo
            helper.setText(htmlContent, true); // true indica que el contenido es HTML

            // Adjuntar una imagen desde el directorio de recursos
            ClassPathResource imageFile = new ClassPathResource("imagen/logo_impulsart.jpg");
            if (imageFile.exists()) {
                helper.addInline("imagen", imageFile);
            }

            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    private String readTemplate(String templatePath) throws IOException {
        try {
            Resource resource = new ClassPathResource("templates/" + templatePath);
            if (!resource.exists()) {
                throw new FileNotFoundException("No se encontró el archivo de plantilla en la ruta: " + resource.getFilename());
            }
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IOException("Error al leer la plantilla desde: " + templatePath, e);
        }
    }
}
