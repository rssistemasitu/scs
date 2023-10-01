package com.rogerio.serviceplatform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rogerio.serviceplatform.common.Payment;
import com.rogerio.serviceplatform.common.TransactionRequest;
import com.rogerio.serviceplatform.common.TransactionResponse;
import com.rogerio.serviceplatform.entity.Subscription;
import com.rogerio.serviceplatform.exception.error.InternalErrorException;
import com.rogerio.serviceplatform.exception.error.NotFoundException;
import com.rogerio.serviceplatform.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RefreshScope
public class SubscriptionService {

    private static Logger log = LoggerFactory.getLogger(SubscriptionService.class);
    private static double SUBSCRIPTION_PRICE = 20.0;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_PAYMENT_SERVICE;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    public TransactionResponse createSubscription(TransactionRequest request) throws JsonProcessingException {
        String message = "";

        Payment payment = Payment.builder().build();
        payment.setAmount(SUBSCRIPTION_PRICE);

        Subscription subscription = Subscription.builder()
                .id(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .price(SUBSCRIPTION_PRICE)
                .dateCreated(LocalDateTime.now())
                .dateLastUpdated(LocalDateTime.now())
                .build();

        payment.setSubscriptionId(subscription.getId());
        Payment paymentResponse =  restTemplate.postForObject(ENDPOINT_PAYMENT_SERVICE + "/doPayment", payment, Payment.class);

        if("success".equals(paymentResponse.getPaymentStatus())) {
            subscription.setStatus(true);
            Subscription subscriptionSaved = subscriptionRepository.save(subscription);
            message = "Payment processing successful and subscription placed";
            return new TransactionResponse(subscriptionSaved, paymentResponse.getAmount(), paymentResponse.getTransactionId(), message);
        }
        throw new InternalErrorException("There is a failure in payment, subscription added to cart");
    }

    public Subscription getSubscriptionById(String id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Assinatura com id %s não foi encontrada.", id)));
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

}
