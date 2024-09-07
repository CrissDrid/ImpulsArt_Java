package com.impulsart.ImpulsArtApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration //Le indica al contenedor de spring que esta es una clase de seguridad al momento de arrancar la aplicacion
@EnableWebSecurity //Indicamos que se activa la seguridad web en nuestra aplicacion y ademas esta sera una clase la cual contendra toda la configuracion referente a la seguridad
public class SecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    //Este bean va a encargarse de verificar la informacion de los usuarios que se loguearan en nuestra api
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Con este bean nos encargaremos de encriptar todas nuestras contraseñas
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Este bean incorporara el filtro de seguridad de json web token que creamos en nuestra clase anterior
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    //Vamos a crear un bean el cual va a establecer una cadena de filtros de seguridad en nuestra aplicacion y es aqui donde determinaremos los permisos segun los roles de usuarios para acceder a nuestra aplicacion
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivar CSRF
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Punto de entrada personalizado
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Política de sesión sin estado
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests

                                // PERMISOS PARA RECURSOS ESTÁTICOS
                                .requestMatchers("/imagen/**").permitAll()
                                // PERMISOS PARA RECURSOS ESTÁTICOS

                                // PERMISOS PARA RECURSOS ESTÁTICOS
                                .requestMatchers("/api/auth/**").permitAll()
                                // PERMISOS PARA RECURSOS ESTÁTICOS

                                //PERMISOS TABLA USUARIO
                                .requestMatchers("/api/usuario/**").permitAll()
                                .requestMatchers("/api/usuario/all").hasAnyAuthority("ADMIN")
                                .requestMatchers("/api/usuario/delete/**").hasAnyAuthority("")
                                //PERMISOS TABLA USUARIO

                                //PERMISOS TABLA CATEGORIA
                                .requestMatchers("/api/categoria/all").hasAnyAuthority("USER", "ADMIN", "ASESOR", "DOMICILIARIO")
                                .requestMatchers("/api/categoria/**").hasAnyAuthority("ADMIN")
                                //PERMISOS TABLA CATEGORIA

                                //PERMISOS TABLA OFERTA
                                .requestMatchers("/api/oferta/**").hasAnyAuthority("USER", "ADMIN", "ASESOR", "DOMICILIARIO")
                                .requestMatchers("/api/oferta/all").hasAnyAuthority("ADMIN")
                                //PERMISOS TABLA OFERTA

                                //PERMISOS TABLA CARRITO
                                .requestMatchers("/api/carrito/**").permitAll()
                                //PERMISOS TABLA CARRITO

                                //PERMISOS TABLA SUBASTA
                                .requestMatchers("/api/subasta/**").permitAll()
                                //PERMISOS TABLA SUBASTA

                                //PERMISOS TABLA OBRA
                                .requestMatchers("/api/obra/**").permitAll()
                                //PERMISOS TABLA OBRA

                                //PERMISOS TABLA DIRECCION
                                .requestMatchers("/api/direccion/**").permitAll()
                                //PERMISOS TABLA DIRECCION

                                //PERMISOS TABLA DIRECCION
                                .requestMatchers("/api/venta/**").permitAll()
                                //PERMISOS TABLA DIRECCION

                                //PERMISOS TABLA REPORTE
                                .requestMatchers("/api/reporte/**").permitAll()
                                //PERMISOS TABLA REPORTE

                                //PERMISOS TABLA PQRS
                                .requestMatchers("/api/pqrs/**").permitAll()
                                //PERMISOS TABLA PQRS

                                //PERMISOS TABLA TIPOPQRS
                                .requestMatchers("/api/tipoPQRS/**").hasAnyAuthority("USER", "ADMIN", "ASESOR", "DOMICILIARIO")
                                //PERMISOS TABLA TIPOPQRS

                                //PERMISOS TABLA Respuesta
                                .requestMatchers("/api/respuesta/**").hasAnyAuthority("USER", "ADMIN", "ASESOR", "DOMICILIARIO")
                                //PERMISOS TABLA Respuesta

                                //PERMISOS API EMAIL
                                .requestMatchers("/api/email/**").permitAll()
                                //PERMISOS API EMAIL

                                .anyRequest().authenticated() // Requerir autenticación para otras solicitudes
                )
                .httpBasic(withDefaults()); // Habilitar autenticación básica HTTP

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
