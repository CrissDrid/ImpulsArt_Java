package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.entities.*;
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
import java.util.*;

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
    private EmailImp emailImp;

 @Autowired
 private CarritoImp carritoImp;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String email = request.get("email").toString();
            if (usuarioImp.existsByEmail(email)) {
                response.put("status", "error");
                response.put("message", "El usuario con el correo " + email + " ya está registrado.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            Usuario usuario = new Usuario();
            usuario.setIdentificacion(Long.parseLong(request.get("identificacion").toString()));
            usuario.setNombre(request.get("nombre").toString());
            usuario.setApellido(request.get("apellido").toString());
            usuario.setFechaNacimiento(LocalDate.parse(request.get("fechaNacimiento").toString()));
            usuario.setEmail(request.get("email").toString());
            usuario.setNumCelular(request.get("numCelular").toString());
            usuario.setContrasena(passwordEncoder.encode(request.get("contrasena").toString()));
            usuario.setUserName(request.get("userName").toString());

            Rol rol = rolImp.findById(Long.parseLong(request.get("fk_Rol").toString()));
            usuario.setRol(rol);

            // Genera un token de verificación
            String verificationToken = UUID.randomUUID().toString();
            usuario.setVerificationToken(verificationToken);
            usuario.setTokenExpiration(LocalDate.now().plusDays(1)); // El token expira en 1 día
            usuario.setVerificado(false); // Marca al usuario como no verificado

            Usuario nuevoUsuario = this.usuarioImp.create(usuario);

            // Crear el carrito para el nuevo usuario
            Carrito carrito = new Carrito();
            carrito.setUsuario(nuevoUsuario);
            carritoImp.create(carrito); // Suponiendo que tienes un servicio para manejar el carrito

            // Enviar correo de verificación con el token
            String verifyUrl = "https://athletic-wholeness-production.up.railway.app/api/usuario/verify?token=" + verificationToken;
            emailImp.enviarCorreoVerificacion(usuario.getEmail(), "Verifica tu cuenta", usuario.getNombre(), verifyUrl);

            response.put("status", "success");
            response.put("message", "Registro Exitoso. Revisa tu correo para verificar tu cuenta.");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al crear el usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam("token") String token) {
        Usuario usuario = usuarioImp.findByVerificationToken(token);

        if (usuario != null) {
            // Verifica si el token ha expirado
            if (usuario.getTokenExpiration().isAfter(LocalDate.now())) {
                usuario.setVerificado(true);
                usuario.setVerificationToken(null); // Limpiar el token después de la verificación
                usuarioImp.update(usuario); // Guarda los cambios en la base de datos

                return ResponseEntity.ok("<!DOCTYPE html><html><head><script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script></head><body><script>Swal.fire({ title: '¡Verificación exitosa!', text: 'Tu cuenta ha sido verificada con éxito.', icon: 'success', confirmButtonText: 'Aceptar' }).then((result) => { if (result.isConfirmed) { window.close(); } });</script></body></html>");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("<!DOCTYPE html><html><head><script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script></head><body><script>Swal.fire({ title: 'Token expirado', text: 'El token de verificación ha expirado.', icon: 'error', confirmButtonText: 'Aceptar' }).then((result) => { if (result.isConfirmed) { window.close(); } });</script></body></html>");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("<!DOCTYPE html><html><head><script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script></head><body><script>Swal.fire({ title: 'Token inválido', text: 'El token de verificación es inválido.', icon: 'error', confirmButtonText: 'Aceptar' }).then((result) => { if (result.isConfirmed) { window.close(); } });</script></body></html>");
        }
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

            // Obtener el usuario autenticado
            Usuario usuario = usuarioImp.findByEmail(email);

            // Verificar si la cuenta está verificada
            if (usuario != null && !usuario.isVerificado()) {
                response.put("status", "error");
                response.put("message", "Por favor, verifica tu cuenta antes de iniciar sesión.");
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN); // O puedes usar otro código de estado si lo prefieres
            }

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
            Usuario usuario = this.usuarioImp.findById(Long.valueOf(identificacion));
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
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long identificacion, @RequestBody Map<String,Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Usuario usuario = this.usuarioImp.findById(identificacion);

            // CAMPOS DE LA TABLA USUARIOS
            usuario.setNombre(request.get("nombre").toString());
            usuario.setApellido(request.get("apellido").toString());
            usuario.setFechaNacimiento(LocalDate.parse(request.get("fechaNacimiento").toString()));
            usuario.setNumCelular(request.get("numCelular").toString());
            usuario.setUserName(request.get("userName").toString());

            // ACTUALIZAR EL USUARIO
            this.usuarioImp.update(usuario);

            // RESPUESTA EXITOSA
            response.put("status", "success");
            response.put("data", "Actualización Exitosa");
        } catch (Exception e) {
            // MANEJO DE ERRORES
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CONTROLLER UPDATE
    @PutMapping("updateRol/{identificacion}")
    public ResponseEntity<Map<String,Object>> updateRol(@PathVariable Long identificacion, @RequestBody Map<String,Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Usuario usuario = this.usuarioImp.findById(identificacion);

            // OBTENER EL ID DEL ROL DESDE LA SOLICITUD (por ejemplo, "rolId" como clave)
            Rol rol = rolImp.findById(Long.parseLong(request.get("rolId").toString()));
            if (rol == null) {
                throw new RuntimeException("Rol no encontrado");
            }

            // ASIGNAR EL ROL AL USUARIO
            usuario.setRol(rol);

            // ACTUALIZAR EL USUARIO
            this.usuarioImp.update(usuario);

            // RESPUESTA EXITOSA
            response.put("status", "success");
            response.put("data", "Actualización Exitosa");
        } catch (Exception e) {
            // MANEJO DE ERRORES
            response.put("status", HttpStatus.BAD_GATEWAY);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//CONTROLLER DELETE
    @DeleteMapping("/delete/{identificacion}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long identificacion){
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
