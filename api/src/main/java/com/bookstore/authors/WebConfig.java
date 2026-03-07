package com.bookstore.authors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//            .addResourceHandler("/uploads/authors/**")
//            .addResourceLocations("file:uploads/authors/");
//    }
//}

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL /uploads/authors/** to filesystem folder (exposes as direct endpoint)
        String absolutePath = Paths.get(System.getProperty("user.dir"), "uploads", "authors").toUri().toString();
        registry.addResourceHandler("/uploads/authors/**")
                .addResourceLocations(absolutePath);
    }
}



