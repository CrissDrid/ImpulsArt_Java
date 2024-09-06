package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.service.imp.EmailImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/email", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.HEAD})
@CrossOrigin("*")
public class EmailController {

    @Autowired
    private EmailImp emailImp;

    @PostMapping("/send")
    public String sendEmail(@RequestParam String destinatario,
                            @RequestParam String asunto,
                            @RequestParam String mensaje,
                            @RequestParam String nombre) {
        try {

            emailImp.enviarCorreo(destinatario, asunto, mensaje, nombre);
            return "Correo enviado con éxito";

        } catch (Exception e) {
            return "Error al enviar correo: " + e.getMessage();
        }
    }

}
