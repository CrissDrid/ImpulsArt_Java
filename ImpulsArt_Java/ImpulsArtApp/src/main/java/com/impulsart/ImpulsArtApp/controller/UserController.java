package com.impulsart.ImpulsArtApp.controller;


import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/usuario", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class UserController {
 @Autowired
    private UsuarioImp usuarioImp;
    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> create(@RequestBody Map<String, Object> request){
        Map<String, Object> response = new HashMap<>();
        try{
            System.out.println("@@@@"+request);
            Usuario usuario = new Usuario();
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

}
