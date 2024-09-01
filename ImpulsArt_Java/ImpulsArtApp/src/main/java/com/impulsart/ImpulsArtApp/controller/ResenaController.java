package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Resena;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.service.imp.ObraImp;
import com.impulsart.ImpulsArtApp.service.imp.ResenaImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/resena")
@CrossOrigin("*")
public class ResenaController {

    @Autowired
    private ResenaImp resenaImp;
    @Autowired
    private UsuarioImp usuarioImp;
    @Autowired
    private ObraImp obraImp;

    // CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@ " + request);

            // INSTANCIA DEL OBJETO RESEÑA
            Resena resena = new Resena();
            // CAMPOS DE LA TABLA RESEÑA
            resena.setComentario(request.get("comentario").toString());
            resena.setPuntuacion(Integer.parseInt(request.get("puntuacion").toString()));

            // Obtener Usuario
            Usuario usuario = usuarioImp.findById(Integer.parseInt(request.get("fk_identificacion").toString()));
            if (usuario == null) {
                response.put("status", "error");
                response.put("data", "Usuario no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            resena.setUsuario(usuario);

            // Obtener Obra
            Obra obra = obraImp.findById(Integer.parseInt(request.get("fkCod_Obra").toString()));
            if (obra == null) {
                response.put("status", "error");
                response.put("data", "Obra no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            resena.setObra(obra);

            // Guardar la reseña
            this.resenaImp.create(resena);

            response.put("status", "success");
            response.put("data", "Registro exitoso");
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // CONTROLLER READ
    // READ ALL
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> findAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Resena> resenas = this.resenaImp.findAll();
            response.put("status", "success");
            response.put("data", resenas);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // READ BY ID
    @GetMapping("/list/{PkCod_Resena}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable("PkCod_Resena") Long PkCod_Resena) {
        Map<String, Object> response = new HashMap<>();
        try {
            Resena resena = resenaImp.findById(PkCod_Resena);
            if (resena != null) {
                response.put("status", "success");
                response.put("data", resena);
            } else {
                response.put("status", "error");
                response.put("data", "Reseña no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // CONTROLLER UPDATE
    @PutMapping("/update/{PkCod_Resena}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable("PkCod_Resena") Long PkCod_Resena,
            @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Resena resena = resenaImp.findById(PkCod_Resena);
            if (resena != null) {
                resena.setComentario(request.get("comentario").toString());
                resena.setPuntuacion(Integer.parseInt(request.get("puntuacion").toString()));
                resenaImp.update(resena);
                response.put("status", "success");
                response.put("data", "Reseña actualizada exitosamente");
            } else {
                response.put("status", "error");
                response.put("data", "Reseña no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // CONTROLLER DELETE
    @DeleteMapping("/delete/{PkCod_Resena}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("PkCod_Resena") Long PkCod_Resena) {
        Map<String, Object> response = new HashMap<>();
        try {
            Resena resena = resenaImp.findById(PkCod_Resena);
            if (resena != null) {
                resenaImp.delete(resena);
                response.put("status", "success");
                response.put("data", "Reseña eliminada exitosamente");
            } else {
                response.put("status", "error");
                response.put("data", "Reseña no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
