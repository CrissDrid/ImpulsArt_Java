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

import java.time.LocalDate;
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

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Crear una instancia de Direccion
            Direccion direccion = new Direccion();

            if (request.get("departamento") != null) {
                direccion.setDepartamento(request.get("departamento").toString());
            } else {
                throw new IllegalArgumentException("El campo 'departamento' no puede ser nulo");
            }

            // Verificar y establecer los campos de la dirección desde el request
            if (request.get("direccion") != null) {
                direccion.setDireccion(request.get("direccion").toString());
            } else {
                throw new IllegalArgumentException("El campo 'direccion' no puede ser nulo");
            }

            if (request.get("ciudad") != null) {
                direccion.setCiudad(request.get("ciudad").toString());
            } else {
                throw new IllegalArgumentException("El campo 'ciudad' no puede ser nulo");
            }

            direccion.setObservaciones(request.get("observaciones").toString());

            // Manejo de la llave foránea (Usuario)
            if (request.get("fkUsuario") != null) {
                Usuario usuario = usuarioImp.findById(Long.parseLong(request.get("fkUsuario").toString()));
                if (usuario != null) {
                    direccion.setUsuario(usuario);
                } else {
                    throw new IllegalArgumentException("Usuario no encontrado para el ID proporcionado");
                }
            } else {
                throw new IllegalArgumentException("El campo 'fkUsuario' no puede ser nulo");
            }

            boolean exists = direccionImp.existsByCiudadAndDireccionAndUsuario(direccion.getCiudad(), direccion.getDireccion(), direccion.getUsuario());

            if (exists) {
                response.put("status", "error");
                response.put("data", "La dirección ya existe para este usuario en la ciudad proporcionada.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Guardar la nueva dirección
            this.direccionImp.create(direccion);

            response.put("status", "success");
            response.put("data", "Registro Exitoso");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", "Hubo un error en el servidor.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    public ResponseEntity<Map<String, Object>> findHistorialDireccion(@PathVariable Long identificacion) {
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

            // Verificar y establecer los campos de la dirección desde el request
            if (request.get("departamento") != null) {
                direccion.setDepartamento(request.get("departamento").toString());
            } else {
                throw new IllegalArgumentException("El campo 'departamento' no puede ser nulo");
            }

            // Verificar y establecer los campos de la dirección desde el request
            if (request.get("direccion") != null) {
                direccion.setDireccion(request.get("direccion").toString());
            } else {
                throw new IllegalArgumentException("El campo 'direccion' no puede ser nulo");
            }

            if (request.get("ciudad") != null) {
                direccion.setCiudad(request.get("ciudad").toString());
            } else {
                throw new IllegalArgumentException("El campo 'ciudad' no puede ser nulo");
            }

            direccion.setObservaciones(request.get("observaciones").toString());

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
