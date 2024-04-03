package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Devolucion;
import com.impulsart.ImpulsArtApp.service.imp.DevolucionImp;
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
@RequestMapping(path = "/api/devolucion", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class DevolucionController {

    @Autowired
    DevolucionImp devolucionImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO ESPECIALIDAD
            Devolucion devolucion = new Devolucion();
            //CAMPOS DE LA TABLA ESPECIALIDAD
            devolucion.setFechaDevolucion(LocalDate.parse(request.get("fechaDevolucion").toString()));
            devolucion.setTotalReembolso(Integer.parseInt(request.get("totalReembolso").toString()));
            devolucion.setComprobanteReembolso(request.get("comprobanteReembolso").toString());
            devolucion.setTotalDevolver(Integer.parseInt(request.get("totalDevolver").toString()));

            this.devolucionImp.create(devolucion);

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
            List<Devolucion> devolucionList = this.devolucionImp.findAll();
            response.put("status", "success");
            response.put("data", devolucionList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pk_CodDevolucion}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pk_CodDevolucion) {
        Map<String, Object> response = new HashMap<>();

        try {
            Devolucion devolucion = this.devolucionImp.findById(pk_CodDevolucion);
            response.put("status", "success");
            response.put("data", devolucion);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pk_CodDevolucion}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pk_CodDevolucion, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Devolucion devolucion = this.devolucionImp.findById(pk_CodDevolucion);

            //CAMPOS DE LA TABLA ESPECIALIDAD
            devolucion.setFechaDevolucion(LocalDate.parse(request.get("fechaDevolucion").toString()));
            devolucion.setTotalReembolso(Integer.parseInt(request.get("totalReembolso").toString()));
            devolucion.setComprobanteReembolso(request.get("comprobanteReembolso").toString());
            devolucion.setTotalDevolver(Integer.parseInt(request.get("totalDevolver").toString()));

            this.devolucionImp.update(devolucion);
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
    @DeleteMapping("/delete/{pk_CodDevolucion}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable  Long pk_CodDevolucion) {
        Map<String, Object> response = new HashMap<>();

        try {
            Devolucion devolucion = this.devolucionImp.findById(pk_CodDevolucion);
            devolucionImp.delete(devolucion);

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
