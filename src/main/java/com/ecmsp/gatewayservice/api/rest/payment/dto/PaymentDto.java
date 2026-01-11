package com.ecmsp.gatewayservice.api.rest.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDto(
    String id,
    String orderId,
    String userId,
    BigDecimal orderTotal,
    String currency,
    String status,
    String paymentLink,
    LocalDateTime expiresAt,
    LocalDateTime paidAt,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}