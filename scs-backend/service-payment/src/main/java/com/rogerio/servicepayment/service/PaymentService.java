package com.rogerio.servicepayment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rogerio.servicepayment.entity.Payment;
import com.rogerio.servicepayment.exception.error.NotFoundException;
import com.rogerio.servicepayment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    private static Logger log = LoggerFactory.getLogger(Payment.class);

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment doPayment(Payment payment) throws JsonProcessingException {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    public String paymentProcessing() {
        return new Random().nextBoolean() ? "success" : "fail";
    }

    @Cacheable("payment-by-subscription")
    public Payment findPaymentBySubscriptionId(String subscriptionId) {
        return paymentRepository.findBySubscriptionId(subscriptionId).orElseThrow(() -> new NotFoundException(String.format("Pagamento com  assinatura %s não foi encontrado.", subscriptionId)));
    }

    @Cacheable("payment-by-id")
    public Payment getById(String id) {
        return paymentRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Pagamento com id %s não foi encontrado.", id)));
    }

    @Cacheable("payments")
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }
}
