package com.rentme.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");

        registry
                .addResourceHandler("/uploads/tools_imgs/**")
                .addResourceLocations(
                        "file:" + System.getProperty("user.dir") + "/uploads/tools_imgs/");

        registry
                .addResourceHandler("/uploads/profile_pics/**")
                .addResourceLocations(
                        "file:" + System.getProperty("user.dir") + "/uploads/profile_pics/");
        registry
                .addResourceHandler("/upload/identity/**")
                .addResourceLocations(
                        "file:" + System.getProperty("user.dir") + "/uploads/identity/");

    }

}
// "api/upload/identity"
