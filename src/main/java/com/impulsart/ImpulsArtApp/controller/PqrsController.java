package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/pqrs", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class PqrsController {

    @Autowired
    PqrsImp pqrsImp;
    @Autowired
    TipoPqrsImp tipoPqrsImp;
    @Autowired
    UsuarioImp usuarioImp;
    @Autowired
    VentaImp ventaImp;
    @Autowired
    EmailImp emailImp;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("estado") String estado,
            @RequestParam(value = "fechaCierre", required = false) String fechaCierre,
            @RequestParam("fkCod_TipoPQRS") Long fkCod_TipoPqrs,
            @RequestParam("usuarioId") Integer usuarioId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Instanciar el objeto PQRS y establecer los campos
            Pqrs pqrs = new Pqrs();
            pqrs.setDescripcion(descripcion);
            pqrs.setEstado(estado);
            pqrs.setFechaCierre(fechaCierre != null ? LocalDate.parse(fechaCierre) : null);

            // Obtener y establecer el tipo de PQRS
            TipoPqrs tipoPQRS = tipoPqrsImp.findById(fkCod_TipoPqrs);
            if (tipoPQRS == null) {
                throw new RuntimeException("Tipo de pqrs no encontrado");
            }
            pqrs.setTipoPQRS(tipoPQRS);

            // Obtener el usuario creador
            Usuario usuarioCreador = usuarioImp.findById(usuarioId);
            if (usuarioCreador == null) {
                throw new RuntimeException("Usuario creador no encontrado");
            }
            pqrs.setUsuario(usuarioCreador);

            // Obtener los usuarios por sus IDs (si se proporcionaron) o asignar un asesor aleatorio
            List<Usuario> usuarios = new ArrayList<>();
            Usuario asesor = usuarioImp.findRandomAsesor();
            if (asesor != null) {
                usuarios.add(asesor); // Añadir el asesor a la lista de usuarios relacionados
            }

            // Sincronizar la relación en el lado de los usuarios
            for (Usuario usuario : usuarios) {
                usuario.getPqrs_asignados().add(pqrs);
            }

            // Establecer la relación de usuarios en el PQRS
            pqrs.setUsuarios(usuarios);

            // Guardar el PQRS en la base de datos
            this.pqrsImp.create(pqrs);

            // Enviar el correo al usuario asignado
            if (!usuarios.isEmpty()) {
                Usuario usuarioAsignado = usuarios.get(0); // Asumiendo que hay al menos un usuario asignado
                emailImp.enviarCorreoPqrsAsignado(
                        usuarioAsignado.getEmail(),
                        "Nuevo PQRS Asignado",
                        usuarioAsignado.getNombre(),
                        "Se le ha asignado un nuevo PQRS. Descripción de la pqrs: " + descripcion
                );
            }

            response.put("status", "success");
            response.put("data", "Registro Exitoso");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //CONTROLLER READ

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND HISTORIAL PQRS

    @GetMapping("/historialPqrs/{identificacion}")
    public ResponseEntity<Map<String, Object>> findHistorialPqrs(@PathVariable Integer identificacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Pqrs> pqrs = this.pqrsImp.findHistorialPqrs(identificacion);
            response.put("status", "success");
            response.put("data", pqrs);
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

    //FIND HISTORIAL PQRS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND PQRS ASIGNADOS A ASESORES

    @GetMapping("/PqrsAsignados/{identificacion}")
    public ResponseEntity<Map<String, Object>> findPqrsAsignadoAsesores(@PathVariable Integer identificacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Pqrs> pqrs = this.pqrsImp.findPqrsAsignadoAsesores(identificacion);
            response.put("status", "success");
            response.put("data", pqrs);
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

    //FIND PQRS ASIGNADOS A ASESORES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //READ ALL
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> findAll() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Pqrs> pqrsList = this.pqrsImp.findAll();
            response.put("status", "success");
            response.put("data", pqrsList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pkCod_Pqrs}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCod_Pqrs) {
        Map<String, Object> response = new HashMap<>();

        try {
            Pqrs pqrs = this.pqrsImp.findById(pkCod_Pqrs);
            response.put("status", "success");
            response.put("data", pqrs);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_Pqrs}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_Pqrs, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Pqrs pqrs = this.pqrsImp.findById(pkCod_Pqrs);

            //CAMPOS DE LA TABLA RECLAMO
            pqrs.setDescripcion(request.get("descripcion").toString());
            pqrs.setEstado(request.get("estado").toString());
            pqrs.setFechaPQRS(LocalDate.parse(request.get("fechaPQRS").toString()));
            pqrs.setFechaCierre(request.get("fechaCierre") != null ? LocalDate.parse(request.get("fechaCierre").toString()) : null);

            this.pqrsImp.update(pqrs);
            response.put("status","success");
            response.put("data","Actualizacion Exitosa");
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER DELETE
    @DeleteMapping("/delete/{pkCod_Pqrs}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long pkCod_Pqrs) {
        Map<String, Object> response = new HashMap<>();

        try {
            Pqrs pqrs = this.pqrsImp.findById(pkCod_Pqrs);
            pqrsImp.delete(pqrs);

            response.put("status", "success");
            response.put("data", "Registro Eliminado Correctamente");
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
