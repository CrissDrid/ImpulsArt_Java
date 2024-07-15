package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Categoria;
import com.impulsart.ImpulsArtApp.entities.Reclamo;
import com.impulsart.ImpulsArtApp.entities.TipoReclamo;
import com.impulsart.ImpulsArtApp.service.imp.CategoriaImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/categoria", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class CategoriaController {

    @Autowired
    CategoriaImp categoriaImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO CATEGORIA
            Categoria categoria = new Categoria();
            //CAMPOS DE LA TABLA CATEGORIA
            categoria.setNombreCategoria(request.get("nombreCategoria").toString());
            categoria.setDescripcionCategoria(request.get("descripcionCategoria").toString());

            this.categoriaImp.create(categoria);

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
            List<Categoria> categoriaList = this.categoriaImp.findAll();
            response.put("status", "success");
            response.put("data", categoriaList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{pkCod_Categoria}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCod_Categoria) {
        Map<String, Object> response = new HashMap<>();

        try {
            Categoria categoria = this.categoriaImp.findById(pkCod_Categoria);
            response.put("status", "success");
            response.put("data", categoria);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_Categoria}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_Categoria, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Categoria categoria = this.categoriaImp.findById(pkCod_Categoria);

            //CAMPOS DE LA TABLA CATEGORIA
            categoria.setNombreCategoria(request.get("nombreCategoria").toString());
            categoria.setDescripcionCategoria(request.get("descripcionCategoria").toString());

            this.categoriaImp.update(categoria);
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
    @DeleteMapping("/delete/{pkCod_Categoria}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long pkCod_Categoria) {
        Map<String, Object> response = new HashMap<>();

        try {
            Categoria categoria = this.categoriaImp.findById(pkCod_Categoria);
            categoriaImp.delete(categoria);

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
