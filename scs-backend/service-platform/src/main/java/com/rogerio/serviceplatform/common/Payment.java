package com.rogerio.serviceplatform.common;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Payment implements Serializable {
    private String paymentId;
    private String paymentStatus;
    private String transactionId;
    private String subscriptionId;
    private Double amount;
}
