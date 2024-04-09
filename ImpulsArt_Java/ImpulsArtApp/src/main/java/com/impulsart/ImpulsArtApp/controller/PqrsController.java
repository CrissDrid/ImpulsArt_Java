package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.EmpleadoImp;
import com.impulsart.ImpulsArtApp.service.imp.PqrsImp;
import com.impulsart.ImpulsArtApp.service.imp.TipoPQRSImp;
import com.impulsart.ImpulsArtApp.service.imp.VentaImp;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/pqrs", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class PqrsController {

    @Autowired
    PqrsImp pqrsImp;
    @Autowired
    EmpleadoImp empleadoImp;
    @Autowired
    VentaImp ventaImp;
    @Autowired
    TipoPQRSImp tipoPQRSImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO ESPECIALIDAD
            Pqrs pqrs = new Pqrs();
            //CAMPOS DE LA TABLA ESPECIALIDAD
            pqrs.setDescripcion(request.get("descripcion").toString());
            pqrs.setMotivo(request.get("motivo").toString());
            pqrs.setRespuesta(request.get("respuesta").toString());
            pqrs.setEstado(request.get("estado").toString());
            pqrs.setFechaPQRS(LocalDate.parse(request.get("fechaPQRS").toString()));
            pqrs.setFechaCierre(request.get("fechaCierre") != null ? LocalDate.parse(request.get("fechaCierre").toString()) : null);

            TipoPQRS tipoPQRS = tipoPQRSImp.findById(Long.valueOf(request.get("FkCod_TipoPQRS").toString()));
            pqrs.setTipoPQRS(tipoPQRS);

            this.pqrsImp.create(pqrs);

            response.put("status", "succses");
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
    @GetMapping("/list/{pkCod_PQRS}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCod_PQRS) {
        Map<String, Object> response = new HashMap<>();

        try {
            Pqrs pqrs = this.pqrsImp.findById(pkCod_PQRS);
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
    @PutMapping("update/{pkCod_PQRS}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_PQRS, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Pqrs pqrs = this.pqrsImp.findById(pkCod_PQRS);

            //CAMPOS DE LA TABLA ESPECIALIDAD
            pqrs.setDescripcion(request.get("descripcion").toString());
            pqrs.setMotivo(request.get("motivo").toString());

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
    @DeleteMapping("/delete/{pkCod_PQRS}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long pkCod_PQRS) {
        Map<String, Object> response = new HashMap<>();

        try {
            Pqrs pqrs = this.pqrsImp.findById(pkCod_PQRS);
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
