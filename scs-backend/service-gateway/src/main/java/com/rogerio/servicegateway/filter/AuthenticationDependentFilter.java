package com.rogerio.servicegateway.filter;

import com.rogerio.servicegateway.exception.error.ForbiddenException;
import com.rogerio.servicegateway.exception.error.InternalErrorException;
import com.rogerio.servicegateway.exception.error.UnauthorizedException;
import com.rogerio.servicegateway.model.UserInfo;
import com.rogerio.servicegateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.rogerio.servicegateway.util.Constants.DEPENDENT;

@Component
public class AuthenticationDependentFilter extends AbstractGatewayFilterFactory<AuthenticationDependentFilter.Config> {

    private static Logger log = LoggerFactory.getLogger(AuthenticationDependentFilter.class);

    @Autowired
    private RouterValidator validator;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${microservice.security-service.endpoints.endpoint.uri}")
    private String ENDPOINT_SECURITY_SERVICE;

    public AuthenticationDependentFilter() {
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
                ResponseEntity<UserInfo> response = jwtUtil.getStatusAndRoles(authHeader);

                if (response.getStatusCode() == HttpStatus.OK) {
                    if(response.getBody().isStatus() && response.getBody().getRoles().contains(DEPENDENT)) {
                        return chain.filter(exchange);
                    } else {
                        throw new ForbiddenException(HttpStatus.FORBIDDEN.toString(), HttpStatus.FORBIDDEN.value());
                    }
                } else {
                    throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.value());
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }

    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
