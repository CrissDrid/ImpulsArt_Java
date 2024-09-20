package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.EmailImp;
import com.impulsart.ImpulsArtApp.service.imp.PqrsImp;
import com.impulsart.ImpulsArtApp.service.imp.RespuestaImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/respuesta", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class RespuestaController {

    @Autowired
    private RespuestaImp respuestaImp;

    @Autowired
    private PqrsImp reclamoImp;

    @Autowired
    private UsuarioImp usuarioImp;

    @Autowired
    EmailImp emailImp;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);

            // Instancia del objeto Respuesta
            Respuesta respuesta = new Respuesta();
            // Campos de la tabla Pedido Personalizado
            respuesta.setComentario(request.get("comentario").toString());
            respuesta.setFechaRespuesta(LocalDate.now());

            Pqrs pqrs = reclamoImp.findById(Long.parseLong(request.get("fk_Pqrs").toString()));
            respuesta.setPqrs(pqrs);

            Usuario usuario = usuarioImp.findById(Long.parseLong(request.get("fk_Identificacion").toString()));
            respuesta.setUsuario(usuario);

            // Guardar la respuesta
            this.respuestaImp.create(respuesta);

            // Actualizar el estado de PQRS a "Respondida"
            pqrs.setEstado("Respondida");
            reclamoImp.update(pqrs); // Asegúrate de tener un método para actualizar PQRS en reclamoImp

            // Enviar correo
            String destinatario = respuesta.getPqrs().getUsuario().getEmail(); // Accede al email desde Pqrs
            String asunto = "Respuesta de su PQRS";
            String mensaje = respuesta.getComentario(); // Accede al comentario directamente desde Respuesta

            emailImp.enviarCorreoRespuesta(asunto, destinatario, mensaje);

            response.put("status", "success");
            response.put("data", "Registro Exitoso");
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER READ
    //READ ALL
    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> findAll(){
        Map<String,Object> response = new HashMap<>();

        try {
            List<Respuesta> respuestaList = this.respuestaImp.findAll();
            response.put("status","success");
            response.put("data", respuestaList);
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //READ ID
    @GetMapping("/list/{pkCod_Respuesta}")
    public ResponseEntity<Map<String,Object>> findById(@PathVariable Long pkCod_Respuesta){
        Map<String,Object> response = new HashMap<>();

        try {
            Respuesta respuesta = this.respuestaImp.findById(pkCod_Respuesta);
            response.put("status","success");
            response.put("data", respuesta);
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_Respuesta}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_Respuesta, @RequestBody Map<String,Object> request){
        Map<String,Object> response = new HashMap<>();
        try {
            Respuesta respuesta = this.respuestaImp.findById(pkCod_Respuesta);

            //CAMPOS DE LA TABLA PEDIDO PERSONALIZADO
            respuesta.setComentario(request.get("comentario").toString());
            respuesta.setFechaRespuesta(LocalDate.parse(request.get("fechaRespuesta").toString()));

            this.respuestaImp.update(respuesta);

            response.put("status","success");
            response.put("data","Actualizacion Exitosa");

        } catch (Exception e){

            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //CONTROLLER DELETE
    @DeleteMapping("/delete/{pkCod_Respuesta}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable Long pkCod_Respuesta){
        Map<String,Object> response = new HashMap<>();

        try {
            Respuesta respuesta = this.respuestaImp.findById(pkCod_Respuesta);
            respuestaImp.delete(respuesta);

            response.put("status","success");
            response.put("data","Registro Eliminado Corectamente");
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
