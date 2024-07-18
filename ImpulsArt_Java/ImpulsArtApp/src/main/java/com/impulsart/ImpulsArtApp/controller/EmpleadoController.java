package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Empleado;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.service.imp.EmpleadoImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/empleado", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class EmpleadoController {

    @Autowired
    EmpleadoImp empleadoImp;
    @Autowired
    UsuarioImp usuarioImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO ESPECIALIDAD
            Empleado empleado = new Empleado();
            //CAMPOS DE LA TABLA ESPECIALIDAD
            empleado.setSalario(Integer.parseInt(request.get("salario").toString()));

            Usuario usuario = usuarioImp.findById(Integer.parseInt(request.get("Fk_Identificacion").toString()));
            empleado.setUsuario(usuario);

            this.empleadoImp.create(empleado);

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
            List<Empleado> empleadoList = this.empleadoImp.findAll();
            response.put("status", "success");
            response.put("data", empleadoList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pkCod_Empleado}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCod_Empleado) {
        Map<String, Object> response = new HashMap<>();

        try {
            Empleado empleado = this.empleadoImp.findById(pkCod_Empleado);
            response.put("status", "success");
            response.put("data", empleado);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_Empleado}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_Empleado, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Empleado empleado = this.empleadoImp.findById(pkCod_Empleado);

            //CAMPOS DE LA TABLA ESPECIALIDAD
            empleado.setSalario(Integer.parseInt(request.get("salario").toString()));

            this.empleadoImp.update(empleado);
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
    @DeleteMapping("/delete/{pkCod_Empleado}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable  Long pkCod_Empleado) {
        Map<String, Object> response = new HashMap<>();

        try {
            Empleado empleado = this.empleadoImp.findById(pkCod_Empleado);
            empleadoImp.delete(empleado);

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
