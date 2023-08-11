package com.rogerio.servicemain.repository;

import com.rogerio.servicemain.entity.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
}
