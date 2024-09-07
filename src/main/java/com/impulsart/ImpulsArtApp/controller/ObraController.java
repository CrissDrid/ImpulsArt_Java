
package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.CategoriaImp;
import com.impulsart.ImpulsArtApp.service.imp.ObraImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import jakarta.persistence.EntityNotFoundException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    //INYECCION DE DEPENDECIAS
@Autowired
private ObraImp obraImp;
@Autowired
private CategoriaImp categoriaImp;
@Autowired
private UsuarioImp usuarioImp;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createObra(
            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("costo") String costo,
            @RequestParam("peso") String peso,
            @RequestParam("ancho") String ancho,
            @RequestParam("alto") String alto,
            @RequestParam("tamano") String tamano,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {

        Map<String, Object> response = new HashMap<>();

        try {
            Obra obra = new Obra();
            obra.setNombreProducto(nombreProducto);
            obra.setDescripcion(descripcion);
            obra.setCosto(costo);
            obra.setPeso(peso);
            obra.setTamano(tamano);
            obra.setAncho(ancho);
            obra.setAlto(alto);
            obra.setCantidad(cantidad);

            // Set the category
            Categoria categoria = categoriaImp.findById(categoriaId);
            if (categoria == null) {
                throw new RuntimeException("Categoría no encontrada");
            }
            obra.setCategoria(categoria);

            if (imagen != null && !imagen.isEmpty()) {
                // Store image as Base64 and get MIME type
                String fileName = StringUtils.cleanPath(imagen.getOriginalFilename());
                String mimeType = imagen.getContentType();
                String imagenBase64 = Base64.getEncoder().encodeToString(imagen.getBytes());
                obra.setImagen(imagenBase64);
                obra.setTipoImagen(mimeType); // Store MIME type
            }

            obraImp.create(obra);

            response.put("status", "success");
            response.put("data", "Creación Exitosa");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

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
    public ResponseEntity<Map<String, Object>> updateObra(
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
            Obra obra = this.obraImp.findById(PkCod_Producto);
            if (obra == null) {
                throw new RuntimeException("Obra no encontrada");
            }

            obra.setNombreProducto(nombreProducto);
            obra.setCosto(costo);
            obra.setPeso(peso);
            obra.setTamano(tamano);
            obra.setAncho(ancho);
            obra.setAlto(alto);
            obra.setCantidad(cantidad);
            obra.setDescripcion(descripcion);

            if (imagen != null && !imagen.isEmpty()) {
                String mimeType = imagen.getContentType();
                String imagenBase64 = Base64.getEncoder().encodeToString(imagen.getBytes());
                obra.setImagen(imagenBase64);
                obra.setTipoImagen(mimeType); // Update MIME type
            }

            Categoria categoria = categoriaImp.findById(categoriaId);
            if (categoria == null) {
                throw new RuntimeException("Categoría no encontrada");
            }
            obra.setCategoria(categoria);

            obraImp.create(obra);

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
