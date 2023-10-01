package com.rogerio.serviceplatform.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Document(collection = "subscriptions")
public class Subscription {
    @Id
    private String id;
    private String userId;
    private double price;
    private boolean status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastUpdated;
}
