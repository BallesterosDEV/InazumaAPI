package com.ballesteros.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración para manejar los recursos de imágenes.
 */
@Configuration
public class ImgConfig implements WebMvcConfigurer {
    /**
     * Añade manejadores de recursos para servir archivos estáticos.
     *
     * @param registry el registro de manejadores de recursos
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/resources/**").addResourceLocations("file:/C:/TFG/resources/");

    }
}
