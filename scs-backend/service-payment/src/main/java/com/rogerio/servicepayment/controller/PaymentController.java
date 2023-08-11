package com.rogerio.servicepayment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rogerio.servicepayment.entity.Payment;
import com.rogerio.servicepayment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.SpanName;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private PaymentService paymentService;

    private static Logger log = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @SpanName("request-id")
    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment) throws JsonProcessingException {
        final Payment resp = paymentService.doPayment(payment);
        log.info("payment-controller", resp.toString());
        return resp;
    }

    @SpanName("request-id")
    @GetMapping("/{orderId}")
    public Payment findPaymentHistoryByOrderId(@PathVariable String orderId) {
        final Payment payment = paymentService.findPaymentBySubscriptionId(orderId);
        log.info("payment-controller", payment.toString());
        return payment;
    }
}
