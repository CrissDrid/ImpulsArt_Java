package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.Carrito;
import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Rol;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.security.JwtGenerador;
import com.impulsart.ImpulsArtApp.service.imp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
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

 @Autowired
 private RolImp rolImp;

 @Autowired
 private AuthenticationManager authenticationManager;

 @Autowired
 private PasswordEncoder passwordEncoder;

 @Autowired
 private JwtGenerador jwtGenerador;

 @Autowired
 private CarritoImp carritoImp;

//CONTROLLER CREATE
@PostMapping("/create")
public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
    Map<String, Object> response = new HashMap<>();
    try {
        // Verifica si el usuario ya existe por el correo electrónico
        String email = request.get("email").toString();
        if (usuarioImp.existsByEmail(email)) {
            response.put("status", "error");
            response.put("message", "El usuario con el correo " + email + " ya está registrado.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        // Instancia del objeto Usuario
        Usuario usuario = new Usuario();
        // Campos de la tabla Usuarios
        usuario.setIdentificacion(Integer.parseInt(request.get("identificacion").toString()));
        usuario.setNombre(request.get("nombre").toString());
        usuario.setApellido(request.get("apellido").toString());
        usuario.setFechaNacimiento(LocalDate.parse(request.get("fechaNacimiento").toString()));
        usuario.setEmail(request.get("email").toString());
        usuario.setNumCelular(request.get("numCelular").toString());
        usuario.setContrasena(passwordEncoder.encode(request.get("contrasena").toString()));
        usuario.setTipoUsuario(request.get("tipoUsuario").toString());
        usuario.setUserName(request.get("userName").toString());

        Rol rol = rolImp.findById(Long.parseLong(request.get("fk_Rol").toString()));
        usuario.setRol(rol);

        // Guarda el usuario
        Usuario nuevoUsuario = this.usuarioImp.create(usuario);

        // Instancia y configura el objeto Carrito
        Carrito carrito = new Carrito();
        carrito.setUsuario(nuevoUsuario);  // Asocia el carrito con el usuario

        // Guarda el carrito
        this.carritoImp.create(carrito);

        response.put("status", "success");
        response.put("data", "Registro Exitoso");
    } catch (Exception e) {
        response.put("status", "error");
        response.put("message", "Error al crear el usuario: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
}


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Extraer email y contraseña del request
            String email = (String) request.get("email");
            String contrasena = (String) request.get("contrasena");

            // Crear el objeto de autenticación con el email y la contraseña
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, contrasena)
            );

            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar el token JWT
            String token = jwtGenerador.generarToken(authentication);

            // Incluir el token en la respuesta
            response.put("status", "success");
            response.put("message", "Login exitoso");
            response.put("token", token);
            response.put("tokenType", "Bearer ");

        } catch (Exception e) {
            // Manejo de excepción, en caso de error en la autenticación
            response.put("status", "error");
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // Devolver la respuesta con el token incluido
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
@PostMapping("/login")
public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> request) {
    Map<String, Object> response = new HashMap<>();
    try {
        // Extraer el correo electrónico y la contraseña del mapa de la solicitud
        String email = (String) request.get("email");
        String contrasena = (String) request.get("contrasena");

        // Buscar usuarios con el correo electrónico coincidente
        List<Usuario> usuarios = usuarioImp.findByEmail(email);

        // Verificar si la lista de usuarios está vacía
        if (usuarios.isEmpty()) {
            // Usuario no encontrado, devolver un error de autorización
            response.put("success", false);
            response.put("message", "Usuario no encontrado");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // Verificar si hay más de un usuario con el mismo correo electrónico
        if (usuarios.size() > 1) {
            // Intentar iniciar sesión con el usuario que coincida con la contraseña
            for (Usuario usuario : usuarios) {
                if (usuario.getContrasena().equals(contrasena)) {
                    // Credenciales válidas, devolver los datos del usuario
                    response.put("success", true);
                    response.put("data", usuario);
                    response.put("identificacion", usuario.getIdentificacion()); // Incluye la identificación en la respuesta
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
            // Si no se encuentra un usuario con la contraseña correcta, devolver error
            response.put("success", false);
            response.put("message", "Credenciales inválidas");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } else {
            // Si solo hay un usuario con el correo electrónico, verificar la contraseña
            Usuario usuario = usuarios.get(0);
            if (usuario.getContrasena().equals(contrasena)) {
                // Credenciales válidas, devolver los datos del usuario
                response.put("success", true);
                response.put("data", usuario);
                response.put("identificacion", usuario.getIdentificacion()); // Incluye la identificación en la respuesta
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // Contraseña incorrecta, devolver error
                response.put("success", false);
                response.put("message", "Credenciales inválidas");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }
    } catch (Exception e) {
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
*/


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
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Integer identificacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            Usuario usuario = this.usuarioImp.findById(identificacion);
            if (usuario != null) {
                response.put("status", "success");
                response.put("data", usuario);
            } else {
                response.put("status", "not_found");
                response.put("data", null);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
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
            usuario.setContrasena(request.get("contrasena").toString());
            usuario.setTipoUsuario(request.get("tipoUsuario").toString());
            usuario.setUserName(request.get("userName").toString());

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
