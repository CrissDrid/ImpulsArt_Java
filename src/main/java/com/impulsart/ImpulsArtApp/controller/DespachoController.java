package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Despacho;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.service.imp.DespachoImp;
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
@RequestMapping(path = "/api/despacho", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")

public class DespachoController {

    //INYECCION DE DEPENDECIAS
    @Autowired
    private DespachoImp despachoImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO ESPECIALIDAD
            Despacho despacho = new Despacho();
            //CAMPOS DE LA TABLA ESPECIALIDAD
            despacho.setEstado(request.get("estado").toString());
            despacho.setComprobante(request.get("comprobante").toString());
            despacho.setFechaEntrega(LocalDate.parse(request.get("fechaEntrega").toString()));

            this.despachoImp.create(despacho);

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
            List<Despacho> despachoList = this.despachoImp.findAll();
            response.put("status", "success");
            response.put("data", despachoList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/asignarDespacho")
    public ResponseEntity<Usuario> asignarDespacho(
            @RequestParam Long pkCod_Despacho,
            @RequestParam int identificacion) {
        Usuario usuarioAsignado = despachoImp.asignarDespachoAUsuario(pkCod_Despacho, identificacion);
        return ResponseEntity.ok(usuarioAsignado);
    }

    //READ DESPACHOS ASIGNADOS
    @GetMapping("/despachosAsignados/{identificacion}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable int identificacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Despacho> despachos = despachoImp.findDespachosAsignados(identificacion);
            response.put("status", "success");
            response.put("data", despachos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", "Error inesperado");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //READ DESPACHOS NO ASIGNADOS
    @GetMapping("/despachosNoAsignados")
    public ResponseEntity<Map<String, Object>> findDespachosNoAsignados() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Despacho> despachoList = this.despachoImp.findDespachos();
            response.put("status", "success");
            response.put("data", despachoList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pkCod_Despacho}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCod_Despacho) {
        Map<String, Object> response = new HashMap<>();

        try {
            Despacho despacho = this.despachoImp.findById(pkCod_Despacho);
            response.put("status", "success");
            response.put("data", despacho);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("/update/{pkCod_Despacho}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_Despacho, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Despacho despacho = this.despachoImp.findById(pkCod_Despacho);

            //CAMPOS DE LA TABLA ESPECIALIDAD
            despacho.setEstado(request.get("estado").toString());
            despacho.setFechaEntrega(LocalDate.parse(request.get("fechaEntrega").toString()));

            this.despachoImp.update(despacho);
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
    @DeleteMapping("/delete/{pkCod_Despacho}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable  Long pkCod_Despacho) {
        Map<String, Object> response = new HashMap<>();

        try {
            Despacho despacho = this.despachoImp.findById(pkCod_Despacho);
            despachoImp.delete(despacho);

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
