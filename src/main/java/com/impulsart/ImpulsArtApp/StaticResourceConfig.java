package com.impulsart.ImpulsArtApp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    // Define the directory for static images
    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/imagen/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure resource handler for static images
        registry.addResourceHandler("/imagen/**")
                .addResourceLocations("file:" + IMAGE_DIRECTORY);
    }
}
