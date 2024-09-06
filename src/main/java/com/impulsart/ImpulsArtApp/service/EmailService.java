package com.impulsart.ImpulsArtApp.service;

public interface EmailService {

    void enviarCorreo(String destinatario, String asunto, String nombre, String mensaje);

    void enviarCorreoSubasta(String destinatario, String asunto, String nombre, String mensaje);

}
