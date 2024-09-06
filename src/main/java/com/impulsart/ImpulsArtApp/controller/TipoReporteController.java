package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.TipoReporte;
import com.impulsart.ImpulsArtApp.service.imp.TipoReporteImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/tipoReporte", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class TipoReporteController {

    @Autowired
    private TipoReporteImp tipoReporteImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> create(@RequestBody Map<String,Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO REPORTE OBRA
            TipoReporte tipoReporte = new TipoReporte();
            //CAMPOS DE LA TABLA PEDIDO PERSONALIZADO
            tipoReporte.setNombre(request.get("nombre").toString());
            tipoReporte.setDescripcion(request.get("descripcion").toString());

            this.tipoReporteImp.create(tipoReporte);

            response.put("status", "success");
            response.put("data", "Registro Exitoso");
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER READ
    //READ ALL
    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> findAll(){
        Map<String,Object> response = new HashMap<>();

        try {
            List<TipoReporte> tipoReporteList = this.tipoReporteImp.findAll();
            response.put("status","success");
            response.put("data", tipoReporteList);
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //READ ID
    @GetMapping("/list/{pkCod_TipoReporte}")
    public ResponseEntity<Map<String,Object>> findById(@PathVariable Long pkCod_TipoReporte){
        Map<String,Object> response = new HashMap<>();

        try {
            TipoReporte tipoReporte = this.tipoReporteImp.findById(pkCod_TipoReporte);
            response.put("status","success");
            response.put("data",tipoReporte);
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_TipoReporte}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_TipoReporte, @RequestBody Map<String,Object> request){
        Map<String,Object> response = new HashMap<>();
        try {
            TipoReporte tipoReporte = this.tipoReporteImp.findById(pkCod_TipoReporte);

            //CAMPOS DE LA TABLA PEDIDO PERSONALIZADO
            tipoReporte.setNombre(request.get("nombre").toString());
            tipoReporte.setDescripcion(request.get("descripcion").toString());

            this.tipoReporteImp.update(tipoReporte);

            response.put("status","success");
            response.put("data","Actualizacion Exitosa");

        } catch (Exception e){

            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //CONTROLLER DELETE
    @DeleteMapping("/delete/{pkCod_TipoReporte}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable Long pkCod_TipoReporte){
        Map<String,Object> response = new HashMap<>();

        try {
            TipoReporte tipoReporte = this.tipoReporteImp.findById(pkCod_TipoReporte);
            tipoReporteImp.delete(tipoReporte);

            response.put("status","success");
            response.put("data","Registro Eliminado Corectamente");
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
