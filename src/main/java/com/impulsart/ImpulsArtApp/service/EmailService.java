package com.impulsart.ImpulsArtApp.service;

public interface EmailService {

    void enviarCorreoVerificacion(String destinatario, String asunto, String nombre, String mensaje);
    void enviarCorreoObraEliminada(String destinatario, String asunto, String mensaje);
    void enviarCorreoVenta(String destinatario, String asunto, String nombre, String detalleCompra, String costoTotal);
    void enviarCorreoPqrsAsignado(String destinatario, String asunto, String nombre, String mensaje);
    void enviarCorreoDespachoAsignado(String destinatario, String asunto, String nombre, String mensaje);
    void enviarCorreo(String destinatario, String asunto, String nombre, String mensaje);
    void enviarCorreoSubasta(String destinatario, String asunto, String nombre, String mensaje);
    void enviarCorreoRespuesta(String asunto, String destinatario, String mensaje);
    void enviarCorreoCompraCreador(String destinatario, String asunto, String nombre, String mensaje);

}
