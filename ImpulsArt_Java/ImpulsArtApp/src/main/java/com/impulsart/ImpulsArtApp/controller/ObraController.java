
package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Categoria;
import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.service.imp.CategoriaImp;
import com.impulsart.ImpulsArtApp.service.imp.ObraImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Clob;
import java.util.*;

@RestController
@RequestMapping(path = "/api/obra",method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class ObraController {

    //Directorio para guardar imagenes
    private static String imageDirectory = System.getProperty("user.dir") + "/ImpulsArt_Java/ImpulsArtApp/src/main/java/com/impulsart/ImpulsArtApp/imagen/";


    //INYECCION DE DEPENDECIAS
@Autowired
private ObraImp obraImp;
@Autowired
private CategoriaImp categoriaImp;

//CONTROLLER CREATE
@PostMapping("/create")
public ResponseEntity<Map<String, Object>> create(
        @RequestParam(value = "imagen", required = false) MultipartFile imagen,
        @RequestParam("nombreProducto") String nombreProducto,
        @RequestParam("costo") String costo,
        @RequestParam("peso") String peso,
        @RequestParam("tamano") String tamano,
        @RequestParam("cantidad") int cantidad,
        @RequestParam("categoria") Long categoriaId,
        @RequestParam("descripcion") String descripcion) {

    Map<String, Object> response = new HashMap<>();

    try {
        // Guardar la imagen y obtener su URL
        String imageUrl = null;
        if (imagen != null) {
            String originalFileName = imagen.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            Path imagePath = Paths.get(imageDirectory, uniqueFileName);
            Files.copy(imagen.getInputStream(), imagePath);

            // Construir la URL completa de la imagen
            imageUrl = "http://localhost:8086/imagen/" + uniqueFileName;
        }

        // Instanciar el objeto Obra y establecer los campos
        Obra obra = new Obra();
        obra.setNombreProducto(nombreProducto);
        obra.setCosto(costo);
        obra.setPeso(peso);
        obra.setTamano(tamano);
        obra.setCantidad(cantidad);
        obra.setDescripcion(descripcion);
        obra.setImagen(imageUrl);

        Categoria categoria = categoriaImp.findById(categoriaId);
        if (categoria == null) {
            throw new RuntimeException("Categoría no encontrada");
        }
        obra.setCategoria(categoria); // Establecer la categoría en la obra

        // Guardar la obra en la base de datos
        this.obraImp.create(obra);

        response.put("status", "success");
        response.put("data", "Registro Exitoso");
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("data", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
//CONTROLLER READ
    //READ ALL
    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> findObraSinSubasta(){
        Map<String,Object> response = new HashMap<>();

        try{
            List<Obra> obraList = this.obraImp.findObrasSinSubasta();
            response.put("status","success");
            response.put("data",obraList);
        }catch (Exception e){
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //FIND BY NAME
    @GetMapping("/nombreProducto/{nombreProducto}")
    public ResponseEntity<Map<String, Object>> findByNombreProductoContaining(@PathVariable String nombreProducto) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Obra> obras = obraImp.findByNombreProductoContainingIgnoreCase(nombreProducto);
            response.put("status", "success");
            response.put("data", obras);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("message", "Error al buscar obras por nombre de producto que contenga la cadena.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    @PutMapping("/update/{pkCod_Producto}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Integer pkCod_Producto, @RequestBody Map<String,Object>request){
        Map<String,Object> response = new HashMap<>();
        try {
            Obra obra = this.obraImp.findById(pkCod_Producto);

            obra.setNombreProducto(request.get("nombreProducto").toString());
            obra.setCosto(request.get("costo").toString());
            obra.setPeso(request.get("peso").toString());
            obra.setTamano(request.get("tamano").toString());
            obra.setCantidad(Integer.parseInt(request.get("cantidad").toString()));
            obra.setDescripcion(request.get("descripcion").toString());

            //CONFIG IMAGEN
            if(request.containsKey("imagen")&& request.get("imagen") !=null){
                obra.setImagen(request.get("imagen").toString());
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
