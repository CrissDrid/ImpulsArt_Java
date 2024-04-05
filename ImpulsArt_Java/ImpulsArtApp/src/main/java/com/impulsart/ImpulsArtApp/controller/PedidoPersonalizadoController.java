package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.PedidoPersonalizado;
import com.impulsart.ImpulsArtApp.service.imp.PedidoPersonalizadoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/pedidoPersonalizado", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class PedidoPersonalizadoController {

    //INYENCCION DE DEPENDECIAS
    @Autowired
        private PedidoPersonalizadoImp pedidoPersonalizadoImp;

//CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> create(@RequestBody Map<String,Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("@@@@" + request);
            //INSTANCIA DEL OBJETO PEDIDO PERSONALIZADO
            PedidoPersonalizado pedidoPersonalizado = new PedidoPersonalizado();
            //CAMPOS DE LA TABLA PEDIDO PERSONALIZADO
            pedidoPersonalizado.setColor(request.get("color").toString());
            pedidoPersonalizado.setMateriales(request.get("materiales").toString());
            pedidoPersonalizado.setContenido(request.get("contenido").toString());
            pedidoPersonalizado.setTamano(request.get("tamaño").toString());
            pedidoPersonalizado.setEstadoPedido(request.get("estadoPedido").toString());

            this.pedidoPersonalizadoImp.create(pedidoPersonalizado);

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
    public ResponseEntity<Map<String,Object>> findAll(){
        Map<String,Object> response = new HashMap<>();

        try {
            List<PedidoPersonalizado> pedidoPersonalizadoList=this.pedidoPersonalizadoImp.findAll();
            response.put("status","success");
            response.put("data",pedidoPersonalizadoList);
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    //READ ID
    @GetMapping("/list/{pk_CodPedido}")
    public ResponseEntity<Map<String,Object>> findById(@PathVariable Integer pk_CodPedido){
        Map<String,Object> response = new HashMap<>();

        try {
            PedidoPersonalizado pedidoPersonalizado = this.pedidoPersonalizadoImp.findById(pk_CodPedido);
            response.put("status","success");
            response.put("data",pedidoPersonalizado);
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//CONTROLLER UPDATE
    @PutMapping("update/{pk_CodPedido}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Integer pk_CodPedido, @RequestBody Map<String,Object> request){
        Map<String,Object> response = new HashMap<>();
        try {
            PedidoPersonalizado pedidoPersonalizado = this.pedidoPersonalizadoImp.findById(pk_CodPedido);

            //CAMPOS DE LA TABLA PEDIDO PERSONALIZADO
            pedidoPersonalizado.setColor(request.get("color").toString());
            pedidoPersonalizado.setMateriales(request.get("materiales").toString());
            pedidoPersonalizado.setContenido(request.get("contenido").toString());
            pedidoPersonalizado.setTamano(request.get("tamaño").toString());
            pedidoPersonalizado.setEstadoPedido(request.get("estadoPedido").toString());

            this.pedidoPersonalizadoImp.update(pedidoPersonalizado);

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
    @DeleteMapping("/delete/{pk_CodPedido}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable Integer pk_CodPedido){
        Map<String,Object> response = new HashMap<>();

        try {
            PedidoPersonalizado pedidoPersonalizado = this.pedidoPersonalizadoImp.findById(pk_CodPedido);
            pedidoPersonalizadoImp.delete(pedidoPersonalizado);

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
