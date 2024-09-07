package com.impulsart.ImpulsArtApp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class imgConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/src/main/resources/static/imagenes/")
                .setCachePeriod(0); // Deshabilitar la caché para desarrollo
    }
}

