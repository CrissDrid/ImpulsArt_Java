package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.service.imp.OfertaImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CREATE

    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String,Object> request){

        Map<String,Object> response = new HashMap<>();

        try {

            System.out.println("@@@@"+request);
            Oferta oferta = new Oferta();
            oferta.setMonto(Integer.parseInt(request.get("Monto").toString()));
            oferta.setFechaOferta(LocalDate.parse(request.get("FechaOferta").toString()));
            oferta.setHoraOferta(LocalTime.parse(request.get("HoraOferta").toString()));
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

}
