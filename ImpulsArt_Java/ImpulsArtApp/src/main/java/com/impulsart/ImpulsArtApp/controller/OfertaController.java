package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.service.imp.OfertaImp;
import com.impulsart.ImpulsArtApp.service.imp.SubastaImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/oferta/",method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.HEAD})
@CrossOrigin("*")

public class OfertaController {
    @Autowired
    private OfertaImp ofertaImp;
    @Autowired
    private SubastaImp subastaImp;
    @Autowired
    UsuarioImp usuarioImp;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CREATE

    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String,Object> request){

        Map<String,Object> response = new HashMap<>();

        try {

            System.out.println("@@@@"+request);
            Oferta oferta = new Oferta();
            oferta.setMonto(Integer.parseInt(request.get("monto").toString()));
            oferta.setFechaOferta(LocalDateTime.parse(request.get("fechaOferta").toString()));

            Usuario usuario = usuarioImp.findById(Integer.parseInt(request.get("fk_Identificacion").toString()));
            oferta.setUsuarios(usuario);

            Subasta subasta = subastaImp.findById(Long.parseLong(request.get("fk_subasta").toString()));
            oferta.setSubastas(subasta);

            this.ofertaImp.create(oferta);

            response.put("status","success");
            response.put("data","Registro Exitoso");

        } catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //CREATE
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND ALL

    @GetMapping("all")
    public ResponseEntity<Map<String, Object>> findAll(){

        Map<String,Object> response = new HashMap<>();

        try {

            List<Oferta> ofertaList = this.ofertaImp.findAll();
            response.put("status","success");
            response.put("data",ofertaList);

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
    //FIND OFERTAS POR SUBASTA

    @GetMapping("/OfertaPorSubasta/{pkCodSubasta}")
    public ResponseEntity<Map<String, Object>> findOfertasBySubasta(@PathVariable Long pkCodSubasta) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Oferta> ofertas = this.ofertaImp.findOfertasBySubasta(pkCodSubasta);
            response.put("status", "success");
            response.put("data", ofertas);
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

    //FIND OFERTAS POR SUBASTA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND OFERTAS POR SUBASTA

    @GetMapping("/OfertaMasAlta/{pkCodSubasta}")
    public ResponseEntity<Map<String, Object>> findOfertaMasAlta(@PathVariable Long pkCodSubasta) {
        Map<String, Object> response = new HashMap<>();

        try {
            Oferta oferta = this.ofertaImp.findOfertaMasAlta(pkCodSubasta);
            response.put("status", "success");
            response.put("data", oferta);
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

    //FIND OFERTAS POR SUBASTA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND ID

    @GetMapping("/list/{PkCod_oferta}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long PkCod_oferta) {
        Map<String, Object> response = new HashMap<>();

        try {
            Oferta oferta = this.ofertaImp.findById(PkCod_oferta);
            response.put("status","success");
            response.put("data",oferta);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //FIND ID
    ///////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //DELETE

    @DeleteMapping("delete/{PkCod_oferta}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable Long PkCod_oferta){
        Map<String,Object> response = new HashMap<>();

        try{
            Oferta oferta = this.ofertaImp.findById(PkCod_oferta);
            ofertaImp.delete(oferta);

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

    @GetMapping("/update/{PkCod_oferta}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long PkCod_oferta, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Oferta oferta = this.ofertaImp.findById(PkCod_oferta);

            oferta.setMonto((Integer)request.get("Monto"));
            oferta.setFechaOferta(LocalDateTime.parse((String) request.get("FechaOferta")));

            response.put("status","success");
            response.put("data",oferta);

            this.ofertaImp.update(oferta);

        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //UPDATE
    ///////////////////////////////////////////////

}
