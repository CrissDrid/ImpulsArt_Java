package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.service.imp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
            usuario.setFechaNacimiento(LocalDate.parse(request.get("fechaNacimiento").toString()));
            usuario.setEmail(request.get("email").toString());
            usuario.setNumCelular(request.get("numCelular").toString());
            usuario.setDireccion(request.get("direccion").toString());
            usuario.setContrasena(request.get("contrasena").toString());
            usuario.setTipoUsuario(request.get("tipoUsuario").toString());
            usuario.setNombreUsuario(request.get("userName").toString());

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

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String email = (String) request.get("email");
            String contrasena = (String) request.get("contrasena");

            // Aquí iría la lógica para buscar el usuario en la base de datos
            Usuario usuario = usuarioImp.findByEmail(email);

            // Verificar si el usuario existe y si la contraseña es correcta
            if (usuario != null && usuario.getContrasena().equals(contrasena)) {
                response.put("success", true);
                response.put("data", "Inicio de sesión exitoso");
                response.put("userName", usuario.getNombreUsuario()); // Incluye el nombre de usuario en la respuesta
            } else {
                response.put("success", false);
                response.put("data", "Credenciales inválidas");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
    @PutMapping("update/{identificacion}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Integer identificacion, @RequestBody Map<String,Object> request){
        Map<String, Object> response = new HashMap<>();
        try{
            Usuario usuario = this.usuarioImp.findById(identificacion);

            //CAMPOS DE LA TABLA USUARIOS
            usuario.setIdentificacion(Integer.parseInt(request.get("identificacion").toString()));
            usuario.setNombre(request.get("nombre").toString());
            usuario.setApellido(request.get("apellido").toString());
            usuario.setEmail(request.get("email").toString());
            usuario.setFechaNacimiento(LocalDate.parse(request.get("fechaNacimiento").toString()));
            usuario.setNumCelular(request.get("numCelular").toString());
            usuario.setDireccion(request.get("direccion").toString());
            usuario.setContrasena(request.get("contrasena").toString());
            usuario.setTipoUsuario(request.get("tipoUsuario").toString());
            usuario.setNombreUsuario(request.get("userName").toString());

            this.usuarioImp.update(usuario);

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
