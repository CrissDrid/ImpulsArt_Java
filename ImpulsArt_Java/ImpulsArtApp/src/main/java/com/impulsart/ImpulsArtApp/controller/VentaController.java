package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.PedidoPersonalizado;
import com.impulsart.ImpulsArtApp.entities.Venta;
import com.impulsart.ImpulsArtApp.service.imp.ObraImp;
import com.impulsart.ImpulsArtApp.service.imp.VentaImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/venta", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class VentaController {

    //INYECCION DE DEPENDECIAS
    @Autowired
    private VentaImp ventaImp;
    @Autowired
    private ObraImp obraImp;

    //CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO VENTA
            Venta venta = new Venta();
            //CAMPOS DE LA TABLA USUARIOS
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            java.sql.Date fechaAnalizada = new java.sql.Date(formateador.parse((String) request.get("fechaVenta")).getTime());
            venta.setFechaVenta(fechaAnalizada);
            venta.setTotalPago(Integer.parseInt(request.get("totalPago").toString()));
            venta.setReciboVenta(request.get("reciboVenta").toString());
            venta.setCantidad(Integer.parseInt(request.get("cantidad").toString()));
            venta.setMetodoPago(request.get("metodoPago").toString());

            Obra obra = obraImp.findById(Integer.parseInt(request.get("FkCod_Producto").toString()));
            venta.setObras(obra);

            this.ventaImp.create(venta);

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
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> findAll() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Venta> ventaList = this.ventaImp.findAll();
            response.put("status", "success");
            response.put("data", ventaList);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //READ ID
    @GetMapping("/list/{PkCod_Venta}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Integer PkCod_Venta) {
        Map<String, Object> response = new HashMap<>();

        try {
            Venta venta = this.ventaImp.findById(PkCod_Venta);
            response.put("status","success");
            response.put("data",venta);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//CONTROLLER UPDATE
    @PutMapping("/update/{PkCod_Venta}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Integer PkCod_Venta, @RequestBody Map<String,Object> request){
        Map<String,Object> response = new HashMap<>();
        try{
            Venta venta = this.ventaImp.findById(PkCod_Venta);

            //CAMPOS DE LA TABLA USUARIOS
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            java.sql.Date fechaAnalizada = new java.sql.Date(formateador.parse((String) request.get("fechaVenta")).getTime());
            venta.setFechaVenta(fechaAnalizada);
            venta.setTotalPago(Integer.parseInt(request.get("totalPago").toString()));
            venta.setReciboVenta(request.get("reciboVenta").toString());
            venta.setCantidad(Integer.parseInt(request.get("cantidad").toString()));
            venta.setMetodoPago(request.get("metodoPago").toString());

            Obra obra = obraImp.findById(Integer.parseInt(request.get("FkCod_Producto").toString()));
            venta.setObras(obra);

            this.ventaImp.update(venta);

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
    @DeleteMapping("/delete/{PkCod_Venta}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer PkCod_Venta) {
        Map<String, Object> response = new HashMap<>();

        try {
            Venta venta = this.ventaImp.findById(PkCod_Venta);
            ventaImp.delete(venta);

            response.put("status","success");
            response.put("data","Registro Eliminado Corectamente");
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

