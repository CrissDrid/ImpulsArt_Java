package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Domiciliario;
import com.impulsart.ImpulsArtApp.entities.RegistroDespacho;
import com.impulsart.ImpulsArtApp.service.imp.DomiciliarioImp;
import com.impulsart.ImpulsArtApp.service.imp.RegistroDespachoImp;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/registroDespacho", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class RegistroDespachoController {

    //INYECCION DE DEPENDECIAS
    @Autowired
    private RegistroDespachoImp registroDespachoImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO DOMICILIARIO
            RegistroDespacho registroDespacho = new RegistroDespacho();
            //CAMPOS DE LA TABLA DOMICILIARIO
            registroDespacho.setFechaEntrega(LocalDate.ofEpochDay(Integer.parseInt(request.get("fechaEntrega").toString())));
            registroDespacho.setFechaSalida(LocalDate.ofEpochDay(Integer.parseInt(request.get("fechaSalida").toString())));
            registroDespacho.setHoraEntrega(LocalTime.ofSecondOfDay(Integer.parseInt(request.get("horaEntrega").toString())));


            this.registroDespachoImp.create(registroDespacho);

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
            List<RegistroDespacho> registroDespachoList = this.registroDespachoImp.findAll();
            response.put("status", "success");
            response.put("data", registroDespachoList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pkCod_registro}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCod_registro) {
        Map<String, Object> response = new HashMap<>();

        try {
            RegistroDespacho registroDespacho = this.registroDespachoImp.findById(pkCod_registro);
            response.put("status", "success");
            response.put("data", registroDespacho);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_registro}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_registro, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            RegistroDespacho registroDespacho = this.registroDespachoImp.findById(pkCod_registro);

            //CAMPOS DE LA TABLA DOMICILIARIO
            registroDespacho.setFechaEntrega(LocalDate.ofEpochDay(Integer.parseInt(request.get("fechaEntrega").toString())));
            registroDespacho.setFechaSalida(LocalDate.ofEpochDay(Integer.parseInt(request.get("fechaSalida").toString())));
            registroDespacho.setHoraEntrega(LocalTime.ofSecondOfDay(Integer.parseInt(request.get("horaEntrega").toString())));

            this.registroDespachoImp.update(registroDespacho);
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
    @DeleteMapping("/delete/{pkCod_registro}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long pkCod_registro) {
        Map<String, Object> response = new HashMap<>();

        try {
            RegistroDespacho registroDespacho = this.registroDespachoImp.findById(pkCod_registro);
            registroDespachoImp.delete(registroDespacho);

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
