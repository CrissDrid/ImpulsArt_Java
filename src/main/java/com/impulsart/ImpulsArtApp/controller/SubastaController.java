package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.CategoriaImp;
import com.impulsart.ImpulsArtApp.service.imp.ObraImp;
import com.impulsart.ImpulsArtApp.service.imp.SubastaImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private static String imageDirectory = System.getProperty("user.dir") + "/src/main/java/com/impulsart/ImpulsArtApp/imagen/";

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
            @RequestParam("costo") BigDecimal costo,
            @RequestParam("peso") String peso,
            @RequestParam("tamano") String tamano,
            @RequestParam("alto") String alto,
            @RequestParam("ancho") String ancho,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("estadoSubasta") String estadoSubasta,
            @RequestParam("precioInicial") BigDecimal precioInicial,
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFinalizacion") String fechaFinalizacion,
            @RequestParam("usuarioIds") List<Integer> usuarioIds) {

        Map<String, Object> response = new HashMap<>();

        try {

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
            subasta.setFechaInicio(LocalDateTime.parse(fechaInicio));
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

    @GetMapping("/historialSubastas/{identificacion}")
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

    @PutMapping("/updatePrice/{pkCodSubasta}")
    public ResponseEntity<Map<String, Object>> updatePrice(
            @PathVariable Long pkCodSubasta,
            @RequestParam("precioInicial") BigDecimal precioInicial) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Buscar la subasta existente
            Subasta subasta = subastaImp.findById(pkCodSubasta);
            if (subasta == null) {
                throw new RuntimeException("Subasta no encontrada");
            }

            // Actualizar solo el precio de la subasta
            subasta.setPrecioInicial(precioInicial);

            // Guardar la subasta actualizada
            subastaImp.update(subasta);

            response.put("status", "success");
            response.put("data", "Precio actualizado con éxito");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //ENCONTRAR CREADOR DE LA SUBASTA


    @GetMapping("/creadorSubasta/{pkCodSubasta}")
    public ResponseEntity<Map<String, Object>> findUsuarioBySubastaId(@PathVariable Long pkCodSubasta) {
        Map<String, Object> response = new HashMap<>();

        try {
            Usuario usuario = this.subastaImp.findUsuarioBySubastaId(pkCodSubasta);
            response.put("status", "success");
            response.put("data", usuario);
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

    //ENCONTRAR CREADOR DE LA SUBASTA
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
            @RequestParam("peso") String peso,
            @RequestParam("tamano") String tamano,
            @RequestParam("alto") String alto,
            @RequestParam("ancho") String ancho,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam("descripcion") String descripcion) {

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
            obra.setPeso(peso);
            obra.setTamano(tamano);
            obra.setAlto(alto);
            obra.setAncho(ancho);
            obra.setDescripcion(descripcion);

            if (imagen != null && !imagen.isEmpty()) {
                String mimeType = imagen.getContentType();
                String imagenBase64 = Base64.getEncoder().encodeToString(imagen.getBytes());
                obra.setImagen(imagenBase64);
                obra.setTipoImagen(mimeType); // Update MIME type
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

}
