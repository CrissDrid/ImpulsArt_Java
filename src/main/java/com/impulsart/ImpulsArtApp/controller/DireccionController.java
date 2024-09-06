package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Direccion;
import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.service.imp.DireccionImp;
import com.impulsart.ImpulsArtApp.service.imp.PqrsImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/direccion", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.HEAD})
@CrossOrigin("*")
public class DireccionController {

    @Autowired
    private DireccionImp direccionImp;
    @Autowired
    private UsuarioImp usuarioImp;

    // CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Crear una instancia de Direccion
            Direccion direccion = new Direccion();

            // Verificar y establecer los campos de la dirección desde el request
            if (request.get("calle") != null) {
                direccion.setCalle(request.get("calle").toString());
            } else {
                throw new IllegalArgumentException("El campo 'calle' no puede ser nulo");
            }

            if (request.get("ciudad") != null) {
                direccion.setCiudad(request.get("ciudad").toString());
            } else {
                throw new IllegalArgumentException("El campo 'ciudad' no puede ser nulo");
            }

            if (request.get("codigoPostal") != null) {
                direccion.setCodigoPostal(request.get("codigoPostal").toString());
            } else {
                throw new IllegalArgumentException("El campo 'codigoPostal' no puede ser nulo");
            }

            // Manejo de la llave foránea (Usuario)
            if (request.get("fkUsuario") != null) {
                Usuario usuario = usuarioImp.findById((int) Long.parseLong(request.get("fkUsuario").toString()));
                if (usuario != null) {
                    direccion.setUsuario(usuario);
                } else {
                    throw new IllegalArgumentException("Usuario no encontrado para el ID proporcionado");
                }
            } else {
                throw new IllegalArgumentException("El campo 'fkUsuario' no puede ser nulo");
            }

            // Guardar la nueva dirección
            this.direccionImp.create(direccion);

            response.put("status", "success");
            response.put("data", "Registro Exitoso");
        } catch (IllegalArgumentException e) {
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
            List<Direccion> direccionList = this.direccionImp.findAll();
            response.put("status", "success");
            response.put("data", direccionList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // READ BY ID
    @GetMapping("/list/{pkCod_Direccion}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCod_Direccion) {
        Map<String, Object> response = new HashMap<>();

        try {
            Direccion direccion = this.direccionImp.findById(pkCod_Direccion);
            response.put("status", "success");
            response.put("data", direccion);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND HISTORIAL DIRECCIONES

    @GetMapping("/historialDirecciones/{identificacion}")
    public ResponseEntity<Map<String, Object>> findHistorialDireccion(@PathVariable Integer identificacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Direccion> direcciones = this.direccionImp.findHistorialDireccion(identificacion);
            response.put("status", "success");
            response.put("data", direcciones);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //FIND HISTORIAL DIRECCIONES
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // CONTROLLER UPDATE
    @PutMapping("/update/{pkCod_Direccion}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long pkCod_Direccion, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Direccion direccion = this.direccionImp.findById(pkCod_Direccion);

            direccion.setCalle(request.get("calle").toString());
            direccion.setCiudad(request.get("ciudad").toString());
            direccion.setCodigoPostal(request.get("codigoPostal").toString());

            this.direccionImp.update(direccion);

            response.put("status", "success");
            response.put("data", "Dirección actualizada exitosamente");
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // CONTROLLER DELETE
    @DeleteMapping("/delete/{pkCod_Direccion}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long pkCod_Direccion) {
        Map<String, Object> response = new HashMap<>();

        try {
            Direccion direccion = this.direccionImp.findById(pkCod_Direccion);
            direccionImp.delete(direccion);

            response.put("status", "success");
            response.put("data", "Dirección eliminada correctamente");
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
