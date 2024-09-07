package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.security.JwtGenerador;
import com.impulsart.ImpulsArtApp.service.imp.CarritoImp;
import com.impulsart.ImpulsArtApp.service.imp.RolImp;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/auth", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.HEAD})
@CrossOrigin("*")
public class Auth {

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

}
