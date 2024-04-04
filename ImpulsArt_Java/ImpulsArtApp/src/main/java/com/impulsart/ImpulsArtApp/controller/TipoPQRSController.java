package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Pqrs;
import com.impulsart.ImpulsArtApp.entities.TipoPQRS;
import com.impulsart.ImpulsArtApp.service.imp.PqrsImp;
import com.impulsart.ImpulsArtApp.service.imp.TipoPQRSImp;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/tipoPQRS", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class TipoPQRSController {

    @Autowired
    TipoPQRSImp tipoPQRSImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO ESPECIALIDAD
            TipoPQRS tipoPQRS = new TipoPQRS();
            //CAMPOS DE LA TABLA ESPECIALIDAD
            tipoPQRS.setNombreTipo(request.get("nombreTipo").toString());
            tipoPQRS.setDescripcion(request.get("descripcion").toString());

            this.tipoPQRSImp.create(tipoPQRS);

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
            List<TipoPQRS> tipoPQRSList = this.tipoPQRSImp.findAll();
            response.put("status", "success");
            response.put("data", tipoPQRSList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pkCod_TipoPQRS}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCod_TipoPQRS) {
        Map<String, Object> response = new HashMap<>();

        try {
            TipoPQRS tipoPQRS = this.tipoPQRSImp.findById(pkCod_TipoPQRS);
            response.put("status", "success");
            response.put("data", tipoPQRS);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_TipoPQRS}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_TipoPQRS, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            TipoPQRS tipoPQRS = this.tipoPQRSImp.findById(pkCod_TipoPQRS);

            //CAMPOS DE LA TABLA ESPECIALIDAD
            tipoPQRS.setNombreTipo(request.get("nombreTipo").toString());
            tipoPQRS.setDescripcion(request.get("descripcion").toString());

            this.tipoPQRSImp.update(tipoPQRS);
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
    @DeleteMapping("/delete/{pkCod_TipoPQRS}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long pkCod_TipoPQRS) {
        Map<String, Object> response = new HashMap<>();

        try {
            TipoPQRS tipoPQRS = this.tipoPQRSImp.findById(pkCod_TipoPQRS);
            tipoPQRSImp.delete(tipoPQRS);

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
