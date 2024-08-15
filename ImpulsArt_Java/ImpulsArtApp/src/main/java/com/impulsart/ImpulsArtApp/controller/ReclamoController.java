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
@RequestMapping(path = "/api/reclamo", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class ReclamoController {

    @Autowired
    ReclamoImp reclamoImp;
    @Autowired
    TipoReclamoImp tipoReclamoImp;
    @Autowired
    UsuarioImp usuarioImp;
    @Autowired
    VentaImp ventaImp;
    @Autowired
    AsesorImp asesorImp;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("motivo") String motivo,
            @RequestParam("respuesta") String respuesta,
            @RequestParam("estado") String estado,
            @RequestParam("fechaPQRS") String fechaPQRS,
            @RequestParam(value = "fechaCierre", required = false) String fechaCierre,
            @RequestParam("fkCod_TipoReclamo") Long fkCodTipoReclamo,
            @RequestParam("usuarioIds") List<Integer> usuarioIds) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Instanciar el objeto Reclamo y establecer los campos
            Reclamo reclamo = new Reclamo();
            reclamo.setDescripcion(descripcion);
            reclamo.setEstado(estado);
            reclamo.setFechaPQRS(LocalDate.parse(fechaPQRS));
            reclamo.setFechaCierre(fechaCierre != null ? LocalDate.parse(fechaCierre) : null);

            // Obtener y establecer el tipo de reclamo
            TipoReclamo tipoReclamo = tipoReclamoImp.findById(fkCodTipoReclamo);
            if (tipoReclamo == null) {
                throw new RuntimeException("Tipo de reclamo no encontrado");
            }
            reclamo.setTipoReclamo(tipoReclamo);

            // Obtener los usuarios por sus IDs
            List<Usuario> usuarios = usuarioIds.stream()
                    .map(id -> usuarioImp.findById(id))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Establecer la relación de usuarios en el reclamo
            reclamo.setUsuarios(usuarios);

            // Sincronizar la relación en el lado de los usuarios
            for (Usuario usuario : usuarios) {
                usuario.getReclamo().add(reclamo);
            }

            // Guardar el reclamo en la base de datos
            this.reclamoImp.create(reclamo);

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

    @GetMapping("/historialReclamos/{identificacion}")
    public ResponseEntity<Map<String, Object>> findHistorialReclamos(@PathVariable Integer identificacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Reclamo> reclamos = this.reclamoImp.findHistorialReclamos(identificacion);
            response.put("status", "success");
            response.put("data", reclamos);
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
            List<Reclamo> reclamoList = this.reclamoImp.findAll();
            response.put("status", "success");
            response.put("data", reclamoList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pkCod_Reclamo}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCod_Reclamo) {
        Map<String, Object> response = new HashMap<>();

        try {
            Reclamo reclamo = this.reclamoImp.findById(pkCod_Reclamo);
            response.put("status", "success");
            response.put("data", reclamo);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_Reclamo}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_Reclamo, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Reclamo reclamo = this.reclamoImp.findById(pkCod_Reclamo);

            //CAMPOS DE LA TABLA RECLAMO
            reclamo.setDescripcion(request.get("descripcion").toString());
            reclamo.setEstado(request.get("estado").toString());
            reclamo.setFechaPQRS(LocalDate.parse(request.get("fechaPQRS").toString()));
            reclamo.setFechaCierre(request.get("fechaCierre") != null ? LocalDate.parse(request.get("fechaCierre").toString()) : null);

            this.reclamoImp.update(reclamo);
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
    @DeleteMapping("/delete/{pkCod_Reclamo}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long pkCod_Reclamo) {
        Map<String, Object> response = new HashMap<>();

        try {
            Reclamo reclamo = this.reclamoImp.findById(pkCod_Reclamo);
            reclamoImp.delete(reclamo);

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
