package com.impulsart.ImpulsArtApp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerador {

    //Metodo para crear un token por medio de la authentication
    public String generarToken(Authentication authentication){
        String email = authentication.getName();
        Date tiempoActual = new Date();
        Date expiracionToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);

        //Linea para generar el token
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
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
