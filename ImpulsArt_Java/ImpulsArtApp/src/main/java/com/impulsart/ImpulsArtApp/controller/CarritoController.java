package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Carrito;
import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.service.imp.CarritoImp;
import com.impulsart.ImpulsArtApp.service.imp.ObraImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/carrito", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@CrossOrigin("*")
public class CarritoController {

    @Autowired
    private CarritoImp carritoImp;

    @Autowired
    private UsuarioImp usuarioImp;

    @Autowired
    private ObraImp obraImp;

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Instanciar el objeto Carrito
            Carrito carrito = new Carrito();

            // Configurar los campos del Carrito
            Usuario usuario = usuarioImp.findById(Integer.parseInt(request.get("fk_Usuario").toString()));
            carrito.setUsuario(usuario);

            this.carritoImp.create(carrito);

            response.put("status", "success");
            response.put("data", "Carrito creado exitosamente");
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // READ ALL
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> findAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Carrito> carritoList = this.carritoImp.findAll();
            response.put("status", "success");
            response.put("data", carritoList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // READ BY ID
    @GetMapping("/list/{PkCod_Carrito}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long PkCod_Carrito) {
        Map<String, Object> response = new HashMap<>();
        try {
            Carrito carrito = this.carritoImp.findById(PkCod_Carrito);
            if (carrito != null) {
                response.put("status", "success");
                response.put("data", carrito);
            } else {
                response.put("status", "not_found");
                response.put("data", null);
            }
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/update/{PkCod_Carrito}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long PkCod_Carrito, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Carrito carrito = this.carritoImp.findById(PkCod_Carrito);

            if (carrito != null) {

                // Actualizar los campos del carrito
                Usuario usuario = usuarioImp.findById(Integer.parseInt(request.get("fk_Usuario").toString()));
                carrito.setUsuario(usuario);

                this.carritoImp.update(carrito);

                response.put("status", "success");
                response.put("data", "Carrito actualizado exitosamente");
            } else {
                response.put("status", "not_found");
                response.put("data", "Carrito no encontrado");
            }
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/delete/{PkCod_Carrito}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long PkCod_Carrito) {
        Map<String, Object> response = new HashMap<>();
        try {
            Carrito carrito = this.carritoImp.findById(PkCod_Carrito);
            if (carrito != null) {
                carritoImp.delete(carrito);
                response.put("status", "success");
                response.put("data", "Carrito eliminado correctamente");
            } else {
                response.put("status", "not_found");
                response.put("data", "Carrito no encontrado");
            }
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}




