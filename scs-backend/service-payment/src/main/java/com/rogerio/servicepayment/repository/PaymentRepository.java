package com.rogerio.servicepayment.repository;

import com.rogerio.servicepayment.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    Optional<Payment> findBySubscriptionId(String subscriptionId);
}
