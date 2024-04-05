package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.RegistroDespacho;
import com.impulsart.ImpulsArtApp.entities.Vehiculo;
import com.impulsart.ImpulsArtApp.service.imp.RegistroDespachoImp;
import com.impulsart.ImpulsArtApp.service.imp.VehiculoImp;
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
@RequestMapping(path = "/api/vehiculo", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class VehiculoController {

    //INYECCION DE DEPENDECIAS
    @Autowired
    private VehiculoImp vehiculoImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO DOMICILIARIO
            Vehiculo vehiculo = new Vehiculo();
            //CAMPOS DE LA TABLA DOMICILIARIO

            vehiculo.setPk_placa(Integer.parseInt(request.get("pk_placa").toString()));
            vehiculo.setMarca(request.get("marca").toString());
            vehiculo.setTipoVehiculo(request.get("tipoVehiculo").toString());
            vehiculo.setCantidadVehiculo(Integer.parseInt(request.get("cantidadVehiculo").toString()));
            vehiculo.setModelo(request.get("modelo").toString());


            this.vehiculoImp.create(vehiculo);

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
            List<Vehiculo> vehiculoList = this.vehiculoImp.findAll();
            response.put("status", "success");
            response.put("data", vehiculoList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pk_placa}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pk_placa) {
        Map<String, Object> response = new HashMap<>();

        try {
            Vehiculo vehiculo = this.vehiculoImp.findById(pk_placa);
            response.put("status", "success");
            response.put("data", vehiculo);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pk_placa}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pk_placa, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Vehiculo vehiculo = this.vehiculoImp.findById(pk_placa);

            //CAMPOS DE LA TABLA DOMICILIARIO
            vehiculo.setMarca(request.get("marca").toString());
            vehiculo.setTipoVehiculo(request.get("tipoVehiculo").toString());
            vehiculo.setCantidadVehiculo(Integer.parseInt(request.get("cantidadVehiculo").toString()));
            vehiculo.setModelo(request.get("modelo").toString());

            this.vehiculoImp.update(vehiculo);
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
    @DeleteMapping("/delete/{pk_placa}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long pk_placa) {
        Map<String, Object> response = new HashMap<>();

        try {
            Vehiculo vehiculo = this.vehiculoImp.findById(pk_placa);
            vehiculoImp.delete(vehiculo);

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
