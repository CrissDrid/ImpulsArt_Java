package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Especialidad;
import com.impulsart.ImpulsArtApp.service.imp.EspecialidadImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/especialidad", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class EspecialidadController {

    //INYECCION DE DEPENDECIAS
    @Autowired
        private EspecialidadImp especialidadImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO ESPECIALIDAD
            Especialidad especialidad = new Especialidad();
            //CAMPOS DE LA TABLA ESPECIALIDAD
            especialidad.setNombreEspecialidad(request.get("nombre").toString());
            especialidad.setDescripcion(request.get("descripcion").toString());

            this.especialidadImp.create(especialidad);

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
            List<Especialidad> especialidadList = this.especialidadImp.findAll();
            response.put("status", "success");
            response.put("data", especialidadList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pk_Especialidad}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Integer pk_Especialidad) {
        Map<String, Object> response = new HashMap<>();

        try {
            Especialidad especialidad = this.especialidadImp.fidnById(pk_Especialidad);
            response.put("status", "success");
            response.put("data", especialidad);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//CONTROLLER UPDATE

//CONTROLLER DELETE
    @DeleteMapping("/delete/{pk_Especialidad}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer pk_Especialidad) {
        Map<String, Object> response = new HashMap<>();

        try {
            Especialidad especialidad = this.especialidadImp.fidnById(pk_Especialidad);
            especialidadImp.delete(especialidad);

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
