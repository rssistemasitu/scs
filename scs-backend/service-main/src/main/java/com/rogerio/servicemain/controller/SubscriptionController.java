package com.rogerio.servicemain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rogerio.servicemain.common.TransactionRequest;
import com.rogerio.servicemain.common.TransactionResponse;
import com.rogerio.servicemain.entity.Subscription;
import com.rogerio.servicemain.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/main/subscriptions")
public class SubscriptionController {
    private SubscriptionService subscriptionService;

    private static Logger log = LoggerFactory.getLogger(MessageController.class);

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/")
    public TransactionResponse createSubscription(@RequestBody TransactionRequest request) throws JsonProcessingException {
        TransactionResponse transactionResponse = subscriptionService.createSubscription(request);
        log.info("main-controller - [flow: create-subscription]");
        return transactionResponse;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscription(@RequestParam String id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        log.info("main-controller - [flow: get-subscription]");
        return ResponseEntity.ok(subscription);
    }

    @GetMapping("/")
    public ResponseEntity<List<Subscription>> getSubscription() {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        log.info("main-controller - [flow: get-all-subscriptions]");
        return ResponseEntity.ok(subscriptions);
    }

}
