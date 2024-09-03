package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

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

            // Crear el contenido HTML del correo
            String html = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<style>" +
                    ".email-container { font-family: Arial, sans-serif; color: #333; }" +
                    ".email-header { background-color: #f4f4f4; padding: 10px; text-align: center; }" +
                    ".email-body { padding: 20px; }" +
                    ".email-footer { background-color: #f4f4f4; padding: 10px; text-align: center; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"email-container\">" +
                    "<div class=\"email-header\">" +
                    "<h1>Bienvenido a ImpulsArt</h1>" +
                    "</div>" +
                    "<div class=\"email-body\">" +
                    "<p>Estimado " + nombre + ",</p>" +
                    "<p>" + mensaje + "</p>" +
                    "<img src=\"cid:imagen\" alt=\"Imagen de prueba\" />" +
                    "</div>" +
                    "<div class=\"email-footer\">" +
                    "<p>&copy; 2024 ImpulsArt</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            // Configura el contenido HTML del correo
            helper.setText(html, true); // true indica que el contenido es HTML

            // Adjuntar una imagen desde el directorio de recursos
            ClassPathResource imageFile = new ClassPathResource("imagen/logo_impulsart.jpg");
            if (imageFile.exists()) {
                helper.addInline("imagen", imageFile);
            }

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }
}
