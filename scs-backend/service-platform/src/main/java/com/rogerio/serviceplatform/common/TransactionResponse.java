package com.rogerio.serviceplatform.common;

import com.rogerio.serviceplatform.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Subscription subscription;
    private double amount;
    private String transaction_id;
    private String message;
}
