package com.rogerio.servicegateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public List<String> openAPIEndpoints = List.of(
            "/api/v1/auth/**",
            "/api/v1/main/messages/**",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openAPIEndpoints
                    .stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
