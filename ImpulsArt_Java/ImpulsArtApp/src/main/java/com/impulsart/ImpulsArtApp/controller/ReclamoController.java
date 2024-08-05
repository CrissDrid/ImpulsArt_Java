package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/reclamo", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class ReclamoController {

    @Autowired
    ReclamoImp reclamoImp;
    @Autowired
    TipoReclamoImp tipoReclamoImp;
    @Autowired
    VentaImp ventaImp;
    @Autowired
    AsesorImp asesorImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO RECLAMO
            Reclamo reclamo = new Reclamo();
            //CAMPOS DE LA TABLA RECLAMO
            reclamo.setDescripcion(request.get("descripcion").toString());
            reclamo.setMotivo(request.get("motivo").toString());
            reclamo.setRespuesta(request.get("respuesta").toString());
            reclamo.setEstado(request.get("estado").toString());
            reclamo.setFechaPQRS(LocalDate.parse(request.get("fechaPQRS").toString()));
            reclamo.setFechaCierre(request.get("fechaCierre") != null ? LocalDate.parse(request.get("fechaCierre").toString()) : null);

            TipoReclamo tipoReclamo = tipoReclamoImp.findById(Long.valueOf(request.get("fkCod_TipoReclamo").toString()));
            reclamo.setTipoReclamo(tipoReclamo);


            this.reclamoImp.create(reclamo);

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
            reclamo.setMotivo(request.get("motivo").toString());
            reclamo.setRespuesta(request.get("respuesta").toString());
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
