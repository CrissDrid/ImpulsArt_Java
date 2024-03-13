package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.service.imp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/usuario", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class UserController {

//INYECCION DE DEPENDECIAS
 @Autowired
    private UsuarioImp usuarioImp;

//CONTROLLER CREATE
    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> create(@RequestBody Map<String, Object> request){
        Map<String, Object> response = new HashMap<>();
        try{
            System.out.println("@@@@"+request);
            //INSTANCIA DEL OBJETO USUARIOS
            Usuario usuario = new Usuario();
            //CAMPOS DE LA TABLA USUARIOS
            usuario.setIdentificacion(Integer.parseInt(request.get("identificacion").toString()));
            usuario.setNombre(request.get("nombre").toString());
            usuario.setApellido(request.get("apellido").toString());
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            java.sql.Date fechaAnalizada = new java.sql.Date(formateador.parse((String) request.get("fechaNacimiento")).getTime());
            usuario.setFechaNacimiento(fechaAnalizada);
            usuario.setEmail(request.get("email").toString());
            usuario.setNumCelular(request.get("numCelular").toString());
            usuario.setDireccion(request.get("direccion").toString());
            usuario.setContrasena(request.get("contrasena").toString());
            usuario.setTipoUsuario(request.get("tipoUsuario").toString());

            this.usuarioImp.create(usuario);

            response.put("status","succses");
            response.put("data","Registro Exitoso");
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//CONTROLLER READ
    //READ ALL
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> findAll(){
        Map<String,Object> response = new HashMap<>();

        try{
            List<Usuario> usuarioList= this.usuarioImp.findAll();
            response.put("status","success");
            response.put("data",usuarioList);
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //READ iD
    @GetMapping("/list/{identificacion}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Integer identificacion){
        Map<String,Object> response = new HashMap<>();

        try{
            Usuario usuario = this.usuarioImp.findById(identificacion);
            response.put("status","success");
            response.put("data",usuario);
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_GATEWAY);
            response.put("data",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
//CONTROLLER UPDATE

//CONTROLLER DELETE
    @DeleteMapping("/delete/{identificacion}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer identificacion){
        Map<String,Object> response = new HashMap<>();

        try{
            Usuario usuario = this.usuarioImp.findById(identificacion);
            usuarioImp.delete(usuario);

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
