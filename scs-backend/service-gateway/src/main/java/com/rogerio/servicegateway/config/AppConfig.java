package com.rogerio.servicegateway.config;

import com.rogerio.servicegateway.exception.GatewayExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ErrorWebExceptionHandler errorWebExceptionHandler(GatewayExceptionHandler exceptionHandler) {
        return exceptionHandler;
    }

    @Bean
    public CorsWebFilter corsFilter() {
        return new CorsWebFilter(corsFilterLogged());
    }

    @Bean
    public CorsConfigurationSource corsFilterLogged() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5173"));
        config.setAllowCredentials(true);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.OPTIONS);
        config.addAllowedMethod(HttpMethod.POST);

        source.registerCorsConfiguration("/**", config);
        return source;
    }


}
