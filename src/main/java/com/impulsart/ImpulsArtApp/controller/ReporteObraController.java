package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.service.imp.ObraImp;
import com.impulsart.ImpulsArtApp.service.imp.ReporteObraImp;
import com.impulsart.ImpulsArtApp.service.imp.TipoReporteImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/reporteObra", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class ReporteObraController {

    @Autowired
    private ReporteObraImp reporteObraImp;

    @Autowired
    private UsuarioImp usuarioImp;

    @Autowired
    private ObraImp obraImp;

    @Autowired
    private TipoReporteImp tipoReporteImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> create(@RequestBody Map<String,Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO REPORTE OBRA
            ReporteObra reporteObra = new ReporteObra();
            //CAMPOS DE LA TABLA PEDIDO PERSONALIZADO
            reporteObra.setComentario(request.get("comentario").toString());
            reporteObra.setEstado("Por resolver");
            reporteObra.setFechaReporte(LocalDate.now());

            Obra obra = obraImp.findById(Integer.parseInt(request.get("fk_obra").toString()));
            reporteObra.setObra(obra);

            TipoReporte tipoReporte = tipoReporteImp.findById(Long.parseLong(request.get("fk_TipoReporte").toString()));
            reporteObra.setTipoReporte(tipoReporte);

            this.reporteObraImp.create(reporteObra);

            response.put("status", "succses");
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
    @GetMapping("/porRevisar")
    public ResponseEntity<Map<String,Object>> findReportesObrasPorRevisar(){
        Map<String,Object> response = new HashMap<>();

        try {
            List<ReporteObra> reporteObraList = this.reporteObraImp.findReportesObrasPorRevisar();
            response.put("status","success");
            response.put("data", reporteObraList);
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ALL
    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> findAll(){
        Map<String,Object> response = new HashMap<>();

        try {
            List<ReporteObra> reporteObraList = this.reporteObraImp.findAll();
            response.put("status","success");
            response.put("data", reporteObraList);
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //READ ID
    @GetMapping("/list/{pkCod_Reporte}")
    public ResponseEntity<Map<String,Object>> findById(@PathVariable Long pkCod_Reporte){
        Map<String,Object> response = new HashMap<>();

        try {
            ReporteObra reporteObra = this.reporteObraImp.findById(pkCod_Reporte);
            response.put("status","success");
            response.put("data", reporteObraImp);
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("update/{pkCod_Reporte}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long pkCod_Reporte, @RequestBody Map<String,Object> request){
        Map<String,Object> response = new HashMap<>();
        try {
            ReporteObra reporteObra = this.reporteObraImp.findById(pkCod_Reporte);

            //CAMPOS DE LA TABLA REPORTE OBRA
            reporteObra.setComentario(request.get("comentario").toString());

            this.reporteObraImp.update(reporteObra);

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
    @DeleteMapping("/delete/{pkCod_Reporte}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable Long pkCod_Reporte){
        Map<String,Object> response = new HashMap<>();

        try {
            ReporteObra reporteObra = this.reporteObraImp.findById(pkCod_Reporte);
            reporteObraImp.delete(reporteObra);

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
