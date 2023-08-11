package com.rogerio.servicegateway.exception;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // Lógica para tratar a exceção recebida
        String errorMessage = "";
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Configuração da resposta de erro
        switch (ex.getMessage()) {
            case "500":
                errorMessage = "Not found authorization header.";
                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                break;
            case "403":
                errorMessage = "Access denied.";
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                break;
            case "401":
                errorMessage = "Not found authorization header";
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                break;
            default:
                int i = Integer.parseInt(ex.getMessage().substring(0, 3));
                if ((i == 401) || (i == 403)) {
                    errorMessage = "Error in Credentials";
                } else {
                    errorMessage = "Error in Gateway application";
                }
                final HttpStatus status = HttpStatus.valueOf(i);
                exchange.getResponse().setStatusCode(status);
                break;
        }

        // Construção do corpo da resposta
        byte[] errorMessageBytes = errorMessage.getBytes();

        // Gravação da resposta no corpo da requisição
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                .bufferFactory().wrap(errorMessageBytes)));
    }

}
