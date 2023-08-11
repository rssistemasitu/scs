package com.rogerio.servicepayment.config;

import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class CustomSleuthConfig {
    @Value("${spring.sleuth.span-name-logger-key:request-id}")
    private String spanNameLoggerKey;

    @PostConstruct
    public void init() {
        // Define o nome do atributo do MDC
        System.setProperty("spring.sleuth.spanNameLoggerKey", spanNameLoggerKey);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE; // Configura a amostragem para sempre ser realizada
    }
}
