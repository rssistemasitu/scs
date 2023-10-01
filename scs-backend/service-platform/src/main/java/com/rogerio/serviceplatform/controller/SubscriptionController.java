package com.rogerio.serviceplatform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rogerio.serviceplatform.common.TransactionRequest;
import com.rogerio.serviceplatform.common.TransactionResponse;
import com.rogerio.serviceplatform.entity.Subscription;
import com.rogerio.serviceplatform.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/platform/subscriptions")
public class SubscriptionController {
    private SubscriptionService subscriptionService;

    private static Logger log = LoggerFactory.getLogger(SubscriptionController.class);

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public TransactionResponse createSubscription(@RequestBody TransactionRequest request) throws JsonProcessingException {
        TransactionResponse transactionResponse = subscriptionService.createSubscription(request);
        log.info("platform-controller - [flow: create-subscription]");
        return transactionResponse;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscription(@RequestParam String id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        log.info("platform-controller - [flow: get-subscription]");
        return ResponseEntity.ok(subscription);
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getSubscription() {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        log.info("platform-controller - [flow: get-all-subscriptions]");
        return ResponseEntity.ok(subscriptions);
    }

}
