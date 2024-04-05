package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.DespachoImp;
import com.impulsart.ImpulsArtApp.service.imp.DomiciliarioImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import com.impulsart.ImpulsArtApp.service.imp.VehiculoImp;
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
@RequestMapping(path = "/api/domiciliario", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class DomiciliarioController {

    //INYECCION DE DEPENDECIAS
    @Autowired
    private DomiciliarioImp domiciliarioImp;
    @Autowired
    private UsuarioImp usuarioImp;
    @Autowired
    private VehiculoImp vehiculoImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO DOMICILIARIO
            Domiciliario domiciliario = new Domiciliario();
            //CAMPOS DE LA TABLA DOMICILIARIO
            domiciliario.setEntregasPendientes(Integer.parseInt(request.get("entregasPendientes").toString()));
            domiciliario.setSalario(Integer.parseInt(request.get("salario").toString()));

            Usuario usuario = usuarioImp.findById(Integer.parseInt(request.get("Fk_Identificacion").toString()));
            domiciliario.setUsuarios(usuario);

            Vehiculo vehiculo = vehiculoImp.findById(Long.valueOf(request.get("fk_placa").toString()));
            domiciliario.setVehiculos(vehiculo);

            this.domiciliarioImp.create(domiciliario);

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
            List<Domiciliario> domiciliarioList = this.domiciliarioImp.findAll();
            response.put("status", "success");
            response.put("data", domiciliarioList);
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
            Domiciliario domiciliario = this.domiciliarioImp.findById(pkCod_Despacho);
            response.put("status", "success");
            response.put("data", domiciliario);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_Domiciliario}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_Domiciliario, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Domiciliario domiciliario = this.domiciliarioImp.findById(pkCod_Domiciliario);

            //CAMPOS DE LA TABLA DOMICILIARIO
            domiciliario.setEntregasPendientes(Integer.parseInt(request.get("entregasPendientes").toString()));
            domiciliario.setSalario(Integer.parseInt(request.get("salario").toString()));

            this.domiciliarioImp.update(domiciliario);
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
    @DeleteMapping("/delete/{pkCod_Domiciliario}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long pkCod_Domiciliario) {
        Map<String, Object> response = new HashMap<>();

        try {
            Domiciliario domiciliario = this.domiciliarioImp.findById(pkCod_Domiciliario);
            domiciliarioImp.delete(domiciliario);

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
