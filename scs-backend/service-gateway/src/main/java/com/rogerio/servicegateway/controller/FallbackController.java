package com.rogerio.servicegateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FallbackController {

    @PostMapping("/mainFallback")
    public ResponseEntity<String> mainFallBack() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Please, contact administrator from Main API.");
    }

    @GetMapping("/paymentFallback")
    public ResponseEntity<String> paymentFallback() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Please, contact administrator from Payment API.");
    }


}
