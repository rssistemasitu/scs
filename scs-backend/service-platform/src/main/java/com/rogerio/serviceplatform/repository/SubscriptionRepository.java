package com.rogerio.serviceplatform.repository;


import com.rogerio.serviceplatform.entity.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
}
