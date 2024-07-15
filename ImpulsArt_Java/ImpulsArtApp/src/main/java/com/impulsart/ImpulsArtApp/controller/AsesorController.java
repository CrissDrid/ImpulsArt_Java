package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Asesor;
import com.impulsart.ImpulsArtApp.entities.Empleado;
import com.impulsart.ImpulsArtApp.service.imp.AsesorImp;
import com.impulsart.ImpulsArtApp.service.imp.EmpleadoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/asesor", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class AsesorController {

    //INYECCION DE DEPENDENCIAS
    @Autowired
    private AsesorImp asesorImp;

    @Autowired
    private EmpleadoImp empleadoImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO ASESOR
            Asesor asesor = new Asesor();
            //CAMPOS DE LA TABLA ASESOR
            asesor.setDisponibilidad(request.get("disponibilidad").toString());
            asesor.setEntregasPendientes(Integer.parseInt(request.get("entregasPendientes").toString()));

            Empleado empleado = empleadoImp.findById(Long.valueOf(request.get("fkCod_Empleado").toString()));
            asesor.setEmpleado(empleado);

            this.asesorImp.create(asesor);

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
            List<Asesor> asesorList = this.asesorImp.findAll();
            response.put("status", "success");
            response.put("data", asesorList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pkCod_Asesor}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCod_Asesor) {
        Map<String, Object> response = new HashMap<>();

        try {
            Asesor asesor = this.asesorImp.findById(pkCod_Asesor);
            response.put("status", "success");
            response.put("data", asesor);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_Asesor}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_Asesor, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Asesor asesor = this.asesorImp.findById(pkCod_Asesor);

            //CAMPOS DE LA TABLA DOMICILIARIO
            asesor.setDisponibilidad(request.get("disponibilidad").toString());
            asesor.setEntregasPendientes(Integer.parseInt(request.get("entregasPendientes").toString()));

            this.asesorImp.update(asesor);
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
    @DeleteMapping("/delete/{pkCod_Asesor}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long pkCod_Asesor) {
        Map<String, Object> response = new HashMap<>();

        try {
            Asesor asesor = this.asesorImp.findById(pkCod_Asesor);
            asesorImp.delete(asesor);

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
