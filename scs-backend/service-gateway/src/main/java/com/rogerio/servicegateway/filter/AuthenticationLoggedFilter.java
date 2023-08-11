package com.rogerio.servicegateway.filter;

import com.rogerio.servicegateway.exception.error.ForbiddenException;
import com.rogerio.servicegateway.exception.error.InternalErrorException;
import com.rogerio.servicegateway.exception.error.UnauthorizedException;
import com.rogerio.servicegateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Component
public class AuthenticationLoggedFilter extends AbstractGatewayFilterFactory<AuthenticationLoggedFilter.Config> {

    private static Logger log = LoggerFactory.getLogger(AuthenticationLoggedFilter.class);

    @Autowired
    private RouterValidator validator;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${microservice.security-service.endpoints.endpoint.uri}")
    private String ENDPOINT_SECURITY_SERVICE;

    public AuthenticationLoggedFilter() {
        super (Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new InternalErrorException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                // Construir a URI com parâmetros, se necessário
                final String parseJwt = jwtUtil.parseJwt(authHeader);
                jwtUtil.validateJwtToken(parseJwt);

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
