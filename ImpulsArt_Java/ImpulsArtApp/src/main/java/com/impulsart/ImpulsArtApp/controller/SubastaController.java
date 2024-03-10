package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.service.imp.SubastaImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/subasta/",method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.HEAD})
@CrossOrigin("*")

public class SubastaController {

    @Autowired
    private SubastaImp subastaImp;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CREATE

    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String,Object> request){

        Map<String,Object> response = new HashMap<>();

        try {

            System.out.println("@@@@"+request);
            Subasta subasta = new Subasta();
            subasta.setEstadoSubasta(request.get("EstadoSubasta").toString());
            subasta.setPrecioInicial(Integer.parseInt(request.get("PrecioInicial").toString()));
            subasta.setFechaInicio(LocalDate.parse(request.get("FechaInicio").toString()));
            subasta.setFechaFinalizacion(LocalDate.parse(request.get("FechaFinalizacion").toString()));
            this.subastaImp.create(subasta);

            response.put("status","success");
            response.put("data","Registro Exitoso");

        } catch (Exception e){

            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //CREATE
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIND

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

    //FIND
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
