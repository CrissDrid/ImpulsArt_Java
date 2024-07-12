package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Reembolso;
import com.impulsart.ImpulsArtApp.entities.Reclamo;
import com.impulsart.ImpulsArtApp.entities.Venta;
import com.impulsart.ImpulsArtApp.service.imp.ReembolsoImp;
import com.impulsart.ImpulsArtApp.service.imp.ReclamoImp;
import com.impulsart.ImpulsArtApp.service.imp.VentaImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/reembolso", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class ReembolsoController {
    @Autowired
    ReembolsoImp devolucionImp;
    @Autowired
    VentaImp ventaImp;
    @Autowired
    ReclamoImp pqrsImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO ESPECIALIDAD
            Reembolso reembolso = new Reembolso();
            //CAMPOS DE LA TABLA ESPECIALIDAD
            reembolso.setFechaDevolucion(LocalDate.parse(request.get("fechaDevolucion").toString()));
            reembolso.setTotalReembolso(Integer.parseInt(request.get("totalReembolso").toString()));
            reembolso.setComprobanteReembolso(request.get("comprobanteReembolso").toString());
            reembolso.setTotalDevolver(Integer.parseInt(request.get("totalDevolver").toString()));


            Venta venta = ventaImp.findById(Integer.parseInt(request.get("FkCod_Venta").toString()));
            reembolso.setVentas(venta);

            Reclamo reclamo = pqrsImp.findById(Long.valueOf(request.get("FkCod_PQRS").toString()));
            reembolso.setReclamo(reclamo);

            this.devolucionImp.create(reembolso);

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
            List<Reembolso> reembolsoList = this.devolucionImp.findAll();
            response.put("status", "success");
            response.put("data", reembolsoList);
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
            Reembolso reembolso = this.devolucionImp.findById(pk_CodDevolucion);
            response.put("status", "success");
            response.put("data", reembolso);
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
            Reembolso reembolso = this.devolucionImp.findById(pk_CodDevolucion);

            //CAMPOS DE LA TABLA ESPECIALIDAD
            reembolso.setFechaDevolucion(LocalDate.parse(request.get("fechaDevolucion").toString()));
            reembolso.setTotalReembolso(Integer.parseInt(request.get("totalReembolso").toString()));
            reembolso.setComprobanteReembolso(request.get("comprobanteReembolso").toString());
            reembolso.setTotalDevolver(Integer.parseInt(request.get("totalDevolver").toString()));

            this.devolucionImp.update(reembolso);
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
            Reembolso reembolso = this.devolucionImp.findById(pk_CodDevolucion);
            devolucionImp.delete(reembolso);

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
