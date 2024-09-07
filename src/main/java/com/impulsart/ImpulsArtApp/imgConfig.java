package com.impulsart.ImpulsArtApp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class imgConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ajusta la ruta según la ubicación real en el entorno de producción
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("file:/app/static/imagenes/")
                .setCachePeriod(3600); // Puedes ajustar el período de caché según sea necesario
    }
}

