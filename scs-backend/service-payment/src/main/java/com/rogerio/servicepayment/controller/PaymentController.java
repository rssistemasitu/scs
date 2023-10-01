package com.rogerio.servicepayment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rogerio.servicepayment.entity.Payment;
import com.rogerio.servicepayment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private PaymentService paymentService;

    private static Logger log = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment) throws JsonProcessingException {
        final Payment resp = paymentService.doPayment(payment);
        log.info("payment-controller - [flow: do-payment]");
        return resp;
    }

    @GetMapping("/subscription/{id}")
    public Payment findPaymentBySubscriptionId(@PathVariable String id) {
        final Payment payment = paymentService.findPaymentBySubscriptionId(id);
        log.info("payment-controller - [flow: get-payment-by-subscription]");
        return payment;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payment>> findAllPayments() {
        List<Payment> payments = paymentService.getAll();
        log.info("payment-controller - [flow: list-payments]");
        return ResponseEntity.ok(payments);
    }
}
