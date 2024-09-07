
package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.CategoriaImp;
import com.impulsart.ImpulsArtApp.service.imp.ObraImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import jakarta.persistence.EntityNotFoundException;
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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/obra",method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class ObraController {

    //Directorio para guardar imagenes
    private static String imageDirectory = System.getProperty("user.dir") + "/src/main/java/com/impulsart/ImpulsArtApp/imagen/";


    //INYECCION DE DEPENDECIAS
@Autowired
private ObraImp obraImp;
@Autowired
private CategoriaImp categoriaImp;
@Autowired
private UsuarioImp usuarioImp;

//CONTROLLER CREATE
@PostMapping("/create")
public ResponseEntity<Map<String, Object>> create(
        @RequestParam(value = "imagen", required = false) MultipartFile imagen,
        @RequestParam("nombreProducto") String nombreProducto,
        @RequestParam("costo") String costo,
        @RequestParam("peso") String peso,
        @RequestParam("ancho") String ancho,
        @RequestParam("alto") String alto,
        @RequestParam(value = "tamano", required = false) String tamano,
        @RequestParam("cantidad") int cantidad,
        @RequestParam("categoriaId") Long categoriaId,
        @RequestParam("descripcion") String descripcion,
        @RequestParam("usuarioIds") List<Integer> usuarioIds) { // Lista de IDs de usuarios

    Map<String, Object> response = new HashMap<>();

    try {
        // Guardar la imagen y obtener su URL
        String imageUrl = null;
        if (imagen != null && !imagen.isEmpty()) {
            String originalFileName = imagen.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            Path imagePath = Paths.get(imageDirectory, uniqueFileName);
            Files.copy(imagen.getInputStream(), imagePath);

            // Construir la URL completa de la imagen
            imageUrl = "/imagen/" + uniqueFileName;
        }

        // Instanciar el objeto Obra y establecer los campos
        Obra obra = new Obra();
        obra.setNombreProducto(nombreProducto);
        obra.setCosto(costo);
        obra.setPeso(peso);
        obra.setTamano(tamano);
        obra.setCantidad(cantidad);
        obra.setDescripcion(descripcion);
        obra.setAncho(ancho);
        obra.setAlto(alto);
        obra.setImagen(imageUrl);

        // Buscar y establecer la categoría
        Categoria categoria = categoriaImp.findById(categoriaId);
        if (categoria == null) {
            throw new RuntimeException("Categoría no encontrada");
        }
        obra.setCategoria(categoria);

        // Obtener los usuarios por sus IDs
        List<Usuario> usuarios = usuarioIds.stream()
                .map(id -> usuarioImp.findById(id))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Establecer la relación de usuarios en la obra
        obra.setUsuarios(usuarios);

        // Sincronizar la relación en el lado de los usuarios
        for (Usuario usuario : usuarios) {
            usuario.getObras().add(obra);
        }

        // Guardar la obra en la base de datos
        this.obraImp.create(obra);

        response.put("status", "success");
        response.put("data", "Registro Exitoso");
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
        response.put("status", "error");
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //UPDATE

    @PutMapping("/update/{PkCod_Producto}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer PkCod_Producto,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("costo") String costo,
            @RequestParam("peso") String peso,
            @RequestParam("ancho") String ancho,
            @RequestParam("alto") String alto,
            @RequestParam(value = "tamano", required = false) String tamano,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam("descripcion") String descripcion) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Buscar la subasta existente
            Obra obra = this.obraImp.findById(PkCod_Producto);
            if (obra == null) {
                throw new RuntimeException("Subasta no encontrada");
            }

            // Actualizar los campos de la obra
            obra.setNombreProducto(nombreProducto);
            obra.setCosto(costo);
            obra.setPeso(peso);
            obra.setTamano(tamano);
            obra.setAncho(ancho);
            obra.setAlto(alto);
            obra.setCantidad(cantidad);
            obra.setDescripcion(descripcion);

            // Actualizar la imagen si se proporciona una nueva
            if (imagen != null && !imagen.isEmpty()) {
                String originalFileName = imagen.getOriginalFilename();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                Path imagePath = Paths.get(imageDirectory, uniqueFileName);
                Files.copy(imagen.getInputStream(), imagePath);

                // Actualizar la URL de la imagen
                String imageUrl = "http://localhost:8086/imagen/" + uniqueFileName;
                obra.setImagen(imageUrl);  // Asegúrate de actualizar la URL en la base de datos
            }

            // Actualizar la categoría
            Categoria categoria = categoriaImp.findById(categoriaId);
            if (categoria == null) {
                throw new RuntimeException("Categoría no encontrada");
            }
            obra.setCategoria(categoria);

            // Guardar la obra actualizada
            obraImp.update(obra);

            response.put("status", "success");
            response.put("data", "Actualización Exitosa");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE
    ///////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND HISTORIAL OBRAS

    @GetMapping("/historialObras/{identificacion}")
    public ResponseEntity<Map<String, Object>> findHistorialObras(@PathVariable Integer identificacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Obra> obras = this.obraImp.findHistorialObras(identificacion);
            response.put("status", "success");
            response.put("data", obras);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //FIND HISTORIAL OBRAS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
