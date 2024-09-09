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
                .cors(withDefaults()) // Habilitar CORS
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

                                // PERMISOS PARA RECURSOS API
                                .requestMatchers("/api/usuario/**").permitAll()
                                .requestMatchers("/api/elemento/**").permitAll()
                                .requestMatchers("/api/categoria/**").permitAll()
                                .requestMatchers("/api/oferta/**").permitAll()
                                .requestMatchers("/api/carrito/**").permitAll()
                                .requestMatchers("/api/subasta/**").permitAll()
                                .requestMatchers("/api/obra/**").permitAll()
                                .requestMatchers("/api/direccion/**").permitAll()
                                .requestMatchers("/api/venta/**").permitAll()
                                .requestMatchers("/api/reporte/**").permitAll()
                                .requestMatchers("/api/pqrs/**").permitAll()
                                .requestMatchers("/api/tipoPQRS/**").hasAnyAuthority("USER", "ADMIN", "ASESOR", "DOMICILIARIO")
                                .requestMatchers("/api/respuesta/**").hasAnyAuthority("USER", "ADMIN", "ASESOR", "DOMICILIARIO")
                                .requestMatchers("/api/email/**").permitAll()

                                // Permitir todas las solicitudes a recursos estáticos y ciertos endpoints sin autenticación
                                .anyRequest().authenticated() // Requerir autenticación para otras solicitudes
                )
                .httpBasic(withDefaults()); // Habilitar autenticación básica HTTP

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
