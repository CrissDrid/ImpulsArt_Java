package com.impulsart.ImpulsArtApp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    // Define the directory for static images
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagen/**")
                .addResourceLocations("file:/src/main/java/com/impulsart/ImpulsArtApp/imagen/")
                .setCachePeriod(0); // Deshabilitar la caché para desarrollo
    }
}
