package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("motivo") String motivo,
            @RequestParam("respuesta") String respuesta,
            @RequestParam("estado") String estado,
            @RequestParam("fechaPQRS") String fechaPQRS,
            @RequestParam(value = "fechaCierre", required = false) String fechaCierre,
            @RequestParam("fkCod_TipoReclamo") Long fkCod_TipoPqrs,
            @RequestParam("usuarioIds") List<Integer> usuarioIds) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Instanciar el objeto Reclamo y establecer los campos
            Pqrs pqrs = new Pqrs();
            pqrs.setDescripcion(descripcion);
            pqrs.setEstado(estado);
            pqrs.setFechaPQRS(LocalDate.parse(fechaPQRS));
            pqrs.setFechaCierre(fechaCierre != null ? LocalDate.parse(fechaCierre) : null);

            // Obtener y establecer el tipo de reclamo
            TipoPqrs tipoPQRS = tipoPqrsImp.findById(fkCod_TipoPqrs);
            if (tipoPQRS == null) {
                throw new RuntimeException("Tipo de pqrs no encontrado");
            }
            pqrs.setTipoPQRS(tipoPQRS);

            // Obtener los usuarios por sus IDs
            List<Usuario> usuarios = usuarioIds.stream()
                    .map(id -> usuarioImp.findById(id))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Establecer la relación de usuarios en el reclamo
            pqrs.setUsuarios(usuarios);

            // Sincronizar la relación en el lado de los usuarios
            for (Usuario usuario : usuarios) {
                usuario.getPqrs().add(pqrs);
            }

            // Guardar el reclamo en la base de datos
            this.pqrsImp.create(pqrs);

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
    //FIND HISTORIAL RECLAMOS

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

    //FIND HISTORIAL RECLAMOS
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
