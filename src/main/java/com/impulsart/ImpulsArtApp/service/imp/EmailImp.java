package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailImp implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarCorreoEstado(String destinatario, String nombre, String estado) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Configura el destinatario y el asunto
            helper.setTo(destinatario);
            helper.setSubject("Actualización del Estado de su Pedido");

            // Leer el archivo de plantilla y reemplazar los marcadores de posición
            String htmlTemplate = readTemplate("EmailEstadoDespacho.html");
            String htmlContent = htmlTemplate
                    .replace("[[nombre]]", nombre)
                    .replace("[[estado]]", estado);

            // Configura el contenido HTML del correo
            helper.setText(htmlContent, true); // true indica que el contenido es HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    @Override
    public void enviarCorreoObraEliminada(String destinatario, String nombreProducto, String mensaje) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Configura el destinatario y el asunto
            helper.setTo(destinatario);
            helper.setSubject("Obra Eliminada");

            // Leer el archivo de plantilla y reemplazar los marcadores de posición
            String htmlTemplate = readTemplate("EmailObraEliminada.html");
            String htmlContent = htmlTemplate
                    .replace("[[nombreProducto]]", nombreProducto)
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
    public void enviarCorreoCompraCreador(String destinatario, String asunto, String nombre, String mensaje) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Configura el destinatario y el asunto
            helper.setTo(destinatario);
            helper.setSubject(asunto);

            // Leer el archivo de plantilla y reemplazar los marcadores de posición
            String htmlTemplate = readTemplate("EmailNotificacionCompraCreador.html");
            String htmlContent = htmlTemplate
                    .replace("[[nombre]]", nombre)
                    .replace("[[mensaje]]", mensaje);

            // Configura el contenido HTML del correo
            helper.setText(htmlContent, true); // true indica que el contenido es HTML

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

            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    @Override
    public void enviarCorreoRespuesta(String asunto, String destinatario, String mensaje) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Configura el destinatario y el asunto
            helper.setTo(destinatario);
            helper.setSubject(asunto);

            // Leer el archivo de plantilla y reemplazar los marcadores de posición
            String htmlTemplate = readTemplate("EmailRespuesta.html");
            String htmlContent = htmlTemplate
                    .replace("[[mensaje]]", mensaje);

            // Configura el contenido HTML del correo
            helper.setText(htmlContent, true); // true indica que el contenido es HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    @Override
    public void enviarCorreoPqrsAsignado(String destinatario, String asunto, String nombre, String mensaje) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Configura el destinatario y el asunto
            helper.setTo(destinatario);
            helper.setSubject(asunto);

            // Leer el archivo de plantilla y reemplazar los marcadores de posición
            String htmlTemplate = readTemplate("EmailPqrsAsignado.html");
            String htmlContent = htmlTemplate
                    .replace("[[nombre]]", nombre)
                    .replace("[[mensaje]]", mensaje);

            // Configura el contenido HTML del correo
            helper.setText(htmlContent, true); // true indica que el contenido es HTML


            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    public void enviarCorreoRespuesta(String destinatario, String mensaje) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Configura el destinatario y el asunto
            helper.setTo(destinatario);

            // Leer el archivo de plantilla y reemplazar los marcadores de posición
            String htmlTemplate = readTemplate("EmailVenta.html");
            String htmlContent = htmlTemplate
                    .replace("[[mensaje]]", mensaje);

            // Configura el contenido HTML del correo
            helper.setText(htmlContent, true); // true indica que el contenido es HTML


            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    @Override
    public void enviarCorreoVenta(String destinatario, String asunto, String nombre, String detalleCompra, String costoTotal) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Configura el destinatario y el asunto
            helper.setTo(destinatario);
            helper.setSubject(asunto);

            // Leer el archivo de plantilla y reemplazar los marcadores de posición
            String htmlTemplate = readTemplate("EmailVenta.html");
            String htmlContent = htmlTemplate
                    .replace("[[nombre]]", nombre)
                    .replace("[[productos]]", detalleCompra)
                    .replace("[[total]]", costoTotal);

            // Configura el contenido HTML del correo
            helper.setText(htmlContent, true); // true indica que el contenido es HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    @Override
    public void enviarCorreoDespachoAsignado(String destinatario, String asunto, String nombre, String mensaje) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Configura el destinatario y el asunto
            helper.setTo(destinatario);
            helper.setSubject(asunto);

            // Leer el archivo de plantilla y reemplazar los marcadores de posición
            String htmlTemplate = readTemplate("EmailDespachoAsignado.html");
            String htmlContent = htmlTemplate
                    .replace("[[nombre]]", nombre)
                    .replace("[[mensaje]]", mensaje);

            // Configura el contenido HTML del correo
            helper.setText(htmlContent, true); // true indica que el contenido es HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    @Override
    public void enviarCorreoVerificacion(String destinatario, String asunto, String nombre, String verifyUrl) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Configura el destinatario y el asunto
            helper.setTo(destinatario);
            helper.setSubject(asunto);

            // Leer el archivo de plantilla y reemplazar los marcadores de posición
            String htmlTemplate = readTemplate("EmailVerificacion.html");
            String htmlContent = htmlTemplate
                    .replace("[[nombre]]", nombre)
                    .replace("[[mensaje]]", "Gracias por registrarte en ImpulsArt. Para completar tu registro, por favor verifica tu dirección de correo electrónico haciendo clic en el siguiente botón:")
                    .replace("[[verificationToken]]", verifyUrl);

            // Configura el contenido HTML del correo
            helper.setText(htmlContent, true); // true indica que el contenido es HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }

    public String readTemplate(String templatePath) throws IOException {
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
