package com.impulsart.ImpulsArtApp.security;

import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtGenerador {

    @Autowired
    private UsuarioImp usuarioImp; // Servicio para cargar los detalles del usuario

    @Autowired
    private CustomUsersDetailsService userDetailsService;

    // Método para crear un token por medio de la autenticación
    public String generarToken(Authentication authentication) {
        // Obtener el usuario autenticado
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername(); // Obtener el email desde UserDetails

        // Cargar el usuario completo desde el servicio de detalles del usuario
        Usuario usuario = usuarioImp.findByEmail(email);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con el email: " + email);
        }

        // Obtener datos del usuario
        Long identificacion = usuario.getIdentificacion();
        String userName = usuario.getUserName();

        Date tiempoActual = new Date();
        Date expiracionToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);

        // Obtener roles de la autenticación
        List<String> rol = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Línea para generar el token
        String token = Jwts.builder()
                .setSubject(email)
                .claim("rol", rol) // Agregar roles como un claim
                .claim("identificacion", identificacion)
                .claim("userName", userName)
                .setIssuedAt(tiempoActual)
                .setExpiration(expiracionToken)
                .signWith(SignatureAlgorithm.HS512, ConstantesSeguridad.JWT_FIRMA)
                .compact();

        return token;
    }

    //Metodo para extraer un username a partir de un token
    public String obtenerUserNameDeJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(ConstantesSeguridad.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    //Metodo para validar el token
    public Boolean validarToken(String token){
        try{

            Jwts.parser().setSigningKey(ConstantesSeguridad.JWT_FIRMA).parseClaimsJws(token);
            return true;

        } catch (Exception e){
             throw new PreAuthenticatedCredentialsNotFoundException("Jwt ha expirado o es incorrecto");
        }
    }

}
