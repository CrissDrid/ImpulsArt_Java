package com.impulsart.ImpulsArtApp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    private static final String IMAGE_DIRECTORY = "/src/main/java/com/impulsart/ImpulsArtApp/imagen/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagen/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + IMAGE_DIRECTORY)
                .setCachePeriod(0); // Configura el período de caché según sea necesario
    }
}
