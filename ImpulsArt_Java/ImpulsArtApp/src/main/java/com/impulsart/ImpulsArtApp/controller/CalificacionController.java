package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Asesor;
import com.impulsart.ImpulsArtApp.entities.Calificacion;
import com.impulsart.ImpulsArtApp.entities.Reclamo;
import com.impulsart.ImpulsArtApp.entities.TipoReclamo;
import com.impulsart.ImpulsArtApp.service.imp.AsesorImp;
import com.impulsart.ImpulsArtApp.service.imp.CalificacionImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/calificacion", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class CalificacionController {

    @Autowired
    CalificacionImp calificacionImp;

    @Autowired
    AsesorImp asesorImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO CALIFICACION
            Calificacion calificacion = new Calificacion();
            //CAMPOS DE LA TABLA CALIFICACION
            calificacion.setPuntaje(Integer.parseInt(request.get("puntaje").toString()));
            calificacion.setComentarios(request.get("comentarios").toString());

            Asesor asesor = asesorImp.findById(Long.valueOf(request.get("FkCod_Asesor").toString()));
            calificacion.setAsesor(asesor);

            this.calificacionImp.create(calificacion);

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
            List<Calificacion> calificacionList = this.calificacionImp.findAll();
            response.put("status", "success");
            response.put("data", calificacionList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pkCod_Calificacion}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCod_Calificacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            Calificacion calificacion = this.calificacionImp.findById(pkCod_Calificacion);
            response.put("status", "success");
            response.put("data", calificacion);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_Calificacion}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_Calificacion, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Calificacion calificacion = this.calificacionImp.findById(pkCod_Calificacion);

            //CAMPOS DE LA TABLA CALIFICACION
            calificacion.setPuntaje(Integer.parseInt(request.get("puntaje").toString()));
            calificacion.setComentarios(request.get("comentarios").toString());

            this.calificacionImp.update(calificacion);
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
    @DeleteMapping("/delete/{pkCod_Calificacion}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long pkCod_Calificacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            Calificacion calificacion = this.calificacionImp.findById(pkCod_Calificacion);
            calificacionImp.delete(calificacion);

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
