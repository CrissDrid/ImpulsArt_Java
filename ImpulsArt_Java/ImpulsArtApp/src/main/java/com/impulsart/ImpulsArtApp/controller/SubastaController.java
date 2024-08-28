package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.CategoriaImp;
import com.impulsart.ImpulsArtApp.service.imp.ObraImp;
import com.impulsart.ImpulsArtApp.service.imp.SubastaImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/subasta/",method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.HEAD})
@CrossOrigin("*")

public class SubastaController {

    //Directorio para guardar imagenes
    private static String imageDirectory = System.getProperty("user.dir") + "/ImpulsArt_Java/ImpulsArtApp/src/main/java/com/impulsart/ImpulsArtApp/imagen/";

    @Autowired
    private SubastaImp subastaImp;
    @Autowired
    private ObraImp obraImp;
    @Autowired
    private CategoriaImp categoriaImp;
    @Autowired
    private UsuarioImp usuarioImp;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CREATE

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("costo") String costo,
            @RequestParam("peso") String peso,
            @RequestParam("tamano") String tamano,
            @RequestParam("alto") String alto,
            @RequestParam("ancho") String ancho,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("estadoSubasta") String estadoSubasta,
            @RequestParam("precioInicial") String precioInicial,
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFinalizacion") String fechaFinalizacion,
            @RequestParam("usuarioIds") List<Integer> usuarioIds) {

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

            // Crear la obra
            Obra obra = new Obra();
            obra.setNombreProducto(nombreProducto);
            obra.setCosto(costo);
            obra.setPeso(peso);
            obra.setAlto(alto);
            obra.setAncho(ancho);
            obra.setTamano(tamano);
            obra.setCantidad(cantidad);
            obra.setDescripcion(descripcion);
            obra.setImagen(imageUrl);

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
            obraImp.create(obra);

            // Crear la subasta
            Subasta subasta = new Subasta();
            subasta.setEstadoSubasta(estadoSubasta);
            subasta.setPrecioInicial(precioInicial);
            subasta.setFechaInicio(LocalDate.parse(fechaInicio));
            subasta.setFechaFinalizacion(LocalDateTime.parse(fechaFinalizacion));
            subasta.setObras(obra);

            // Guardar la subasta en la base de datos
            subastaImp.create(subasta);

            response.put("status", "success");
            response.put("data", "Registro Exitoso");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //CREATE
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND HISTORIAL OBRAS SUBASTAS

    @GetMapping("/historialObraSubastas/{identificacion}")
    public ResponseEntity<Map<String, Object>> findHistorialObrasSubasta(@PathVariable Integer identificacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Subasta> subastas = this.subastaImp.findHistorialObrasSubasta(identificacion);
            response.put("status", "success");
            response.put("data", subastas);
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

    //FIND HISTORIAL OBRAS SUBASTAS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND ALL

    @GetMapping("all")
    public ResponseEntity<Map<String, Object>> findAll(){

        Map<String,Object> response = new HashMap<>();

        try {

            List<Subasta> subastaList = this.subastaImp.findAll();
            response.put("status","success");
            response.put("data",subastaList);

        } catch (Exception e){

            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //FIND ALL
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND ID

    @GetMapping("/list/{pkCodSubasta}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long pkCodSubasta) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Subasta> subastas = this.subastaImp.findSubastaByIdWithObras(pkCodSubasta);
            response.put("status", "success");
            response.put("data", subastas);
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


    //FIND ID
    ///////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND SUBASTA Y OBRAS

    @GetMapping("subastaYobras")
    public ResponseEntity<Map<String, Object>> findSubastaAndObras(){

        Map<String,Object> response = new HashMap<>();

        try {

            List<Subasta> subastaList = this.subastaImp.findSubastaAndObras();
            response.put("status","success");
            response.put("data",subastaList);

        } catch (Exception e){

            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //FIND SUBASTA Y OBRAS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/estado/{estadoSubasta}")
    public ResponseEntity<Map<String, Object>> findByEstadoSubastaContainingIgnoreCase(@PathVariable String estadoSubasta) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Subasta> subastas = subastaImp.findByEstadoSubastaContainingIgnoreCase(estadoSubasta);
            response.put("status", "success");
            response.put("data", subastas);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("message", "Error al buscar obras por nombre de producto que contenga la cadena.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //DELETE

    @DeleteMapping("delete/{pkCodSubasta}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable Long pkCodSubasta){
        Map<String,Object> response = new HashMap<>();

        try{
            Subasta subasta = this.subastaImp.findById(pkCodSubasta);
            subastaImp.delete(subasta);

            response.put("status","success");
            response.put("data","Registro Eliminado Correctamente");
        }catch (Exception e){
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //DELETE
    ///////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //UPDATE

    @PutMapping("/update/{pkCodSubasta}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long pkCodSubasta,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("costo") String costo,
            @RequestParam("peso") String peso,
            @RequestParam("tamano") String tamano,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("alto") String alto,
            @RequestParam("ancho") String ancho,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precioInicial") String precioInicial,
            @RequestParam("fechaFinalizacion") String fechaFinalizacion) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Buscar la subasta existente
            Subasta subasta = this.subastaImp.findById(pkCodSubasta);
            if (subasta == null) {
                throw new RuntimeException("Subasta no encontrada");
            }

            // Actualizar la obra asociada a la subasta
            Obra obra = subasta.getObras();
            if (obra == null) {
                throw new RuntimeException("Obra no encontrada");
            }

            // Actualizar los campos de la obra
            obra.setNombreProducto(nombreProducto);
            obra.setCosto(costo);
            obra.setPeso(peso);
            obra.setTamano(tamano);
            obra.setAlto(alto);
            obra.setAncho(ancho);
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

            // Actualizar los campos de la subasta
            subasta.setPrecioInicial(precioInicial);
            subasta.setFechaFinalizacion(LocalDateTime.parse(fechaFinalizacion));

            // Guardar la subasta actualizada
            subastaImp.update(subasta);

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

}
