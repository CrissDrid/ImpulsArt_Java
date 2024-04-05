package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.service.imp.ObraImp;
import com.impulsart.ImpulsArtApp.service.imp.SubastaImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    @Autowired
    private ObraImp obraImp;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CREATE

    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String,Object> request){

        Map<String,Object> response = new HashMap<>();

        try {

            System.out.println("@@@@"+request);
            Subasta subasta = new Subasta();
            subasta.setEstadoSubasta(request.get("estadoSubasta").toString());
            subasta.setPrecioInicial(Integer.parseInt(request.get("precioInicial").toString()));
            subasta.setFechaInicio(LocalDate.parse(request.get("fechaInicio").toString()));
            subasta.setFechaFinalizacion(LocalDate.parse(request.get("fechaFinalizacion").toString()));

            Obra obra = obraImp.findById(Integer.parseInt(request.get("FkCod_Producto").toString()));
            subasta.setObras(obra);

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
            Subasta subasta = this.subastaImp.findById(pkCodSubasta);
            response.put("status","success");
            response.put("data",subasta);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //FIND ID
    ///////////////////////////////////////////////

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

    @GetMapping("/update/{pkCodSubasta}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long pkCodSubasta, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Subasta subasta = this.subastaImp.findById(pkCodSubasta);

            subasta.setEstadoSubasta(request.get("estadoSubasta").toString());
            subasta.setPrecioInicial(Integer.parseInt(request.get("precioInicial").toString()));
            subasta.setFechaInicio(LocalDate.parse(request.get("fechaInicio").toString()));
            subasta.setFechaFinalizacion(LocalDate.parse(request.get("fechaFinalizacion").toString()));

            response.put("status","success");
            response.put("data","Actualizacion Exitosa");

            this.subastaImp.update(subasta);

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
