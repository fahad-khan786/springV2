package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow CORS on all endpoints
                        .allowedOrigins("*") // AEM originhttp://localhost:4502
                        .allowedMethods("GET", "POST")
                        .allowedHeaders("*");
            }
        };
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // Disable CSRF (useful for APIs)
//        http.csrf().disable();
//
//        // Authorize requests
//        http.authorizeRequests()
//                .antMatchers("/api/**").permitAll() // allow all requests to /api/**
//                .anyRequest().authenticated();      // all other requests require authentication
//
//        return http.build();
//    }
}

