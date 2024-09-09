package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Carrito;
import com.impulsart.ImpulsArtApp.entities.ElementoCarrito;
import com.impulsart.ImpulsArtApp.service.imp.ElementoCarritoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/elemento", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@CrossOrigin("*")
public class ElementoCarritoController {

    @Autowired
    ElementoCarritoImp elementoCarritoImp;

    // DELETE
    @DeleteMapping("/delete/{PkCod_Elemento}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long PkCod_Elemento) {
        Map<String, Object> response = new HashMap<>();
        try {
            ElementoCarrito elementoCarrito = this.elementoCarritoImp.findById(PkCod_Elemento);
            if (elementoCarrito != null) {
                elementoCarritoImp.delete(elementoCarrito);
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
