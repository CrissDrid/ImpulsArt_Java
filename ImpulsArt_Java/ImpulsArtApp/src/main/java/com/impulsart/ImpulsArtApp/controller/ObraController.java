
package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.service.imp.ObraImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Clob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/obra",method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class ObraController {

//INYECCION DE DEPENDECIAS
@Autowired
    private ObraImp obraImp;

//CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> create(@RequestBody Map<String, Object> request){
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@"+request);
            //INSTACIA DEL OBJETO OBRA
            Obra obra = new Obra();
            //CAMPOS DE LA TABLA OBRA
            obra.setNombre(request.get("nombreProducto").toString());
            obra.setCosto(Integer.parseInt(request.get("costo").toString()));
            obra.setPeso(request.get("peso").toString());
            obra.setTamano(request.get("tamaño").toString());
            obra.setCantidad(Integer.parseInt(request.get("cantidad").toString()));
            obra.setCategoria(request.get("categoria").toString());
            obra.setDescripcion(request.get("descripcion").toString());

            //CONFIG IMAGEN
            if(request.containsKey("imagen")&& request.get("imagen") !=null){
                obra.setImagen(request.get("imagen").toString().getBytes());
            }

            this.obraImp.create(obra);

            response.put("status","succses");
            response.put("data","Registro Exitoso");
        }catch (Exception e){
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//CONTROLLER READ
    //READ ALL
    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> findAll(){
        Map<String,Object> response = new HashMap<>();

        try{
            List<Obra> obraList = this.obraImp.findAll();
            response.put("status","success");
            response.put("data",obraList);
        }catch (Exception e){
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //READ ID
    @GetMapping("/list/{PkCod_Producto}")
    public ResponseEntity<Map<String,Object>> findById(@PathVariable Integer PkCod_Producto){
        Map<String,Object> response = new HashMap<>();

        try{
            Obra obra = this.obraImp.findById(PkCod_Producto);
            response.put("status","success");
            response.put("data",obra);
        }catch (Exception e){
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//CONTROLLER UPDATE
    @PutMapping("update/{PkCod_Producto}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Integer PkCod_Producto, @RequestBody Map<String,Object>request){
        Map<String,Object> response = new HashMap<>();
        try {
            Obra obra = this.obraImp.findById(PkCod_Producto);

            obra.setNombre(request.get("nombreProducto").toString());
            obra.setCosto(Integer.parseInt(request.get("costo").toString()));
            obra.setPeso(request.get("peso").toString());
            obra.setTamano(request.get("tamaño").toString());
            obra.setCantidad(Integer.parseInt(request.get("cantidad").toString()));
            obra.setCategoria(request.get("categoria").toString());
            obra.setDescripcion(request.get("descripcion").toString());

            //CONFIG IMAGEN
            if(request.containsKey("imagen")&& request.get("imagen") !=null){
                obra.setImagen(request.get("imagen").toString().getBytes());
            }

            this.obraImp.update(obra);

            response.put("status","success");
            response.put("data","Actualizacion Exitosa");
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//CONTROLLER DELETE
    @DeleteMapping("/delete/{PkCod_Producto}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable Integer PkCod_Producto){
        Map<String,Object> response = new HashMap<>();

        try{
            Obra obra = this.obraImp.findById(PkCod_Producto);
            obraImp.delete(obra);

            response.put("status","success");
            response.put("data","Registro Eliminado Correctamente");
        }catch (Exception e){
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
}
}
