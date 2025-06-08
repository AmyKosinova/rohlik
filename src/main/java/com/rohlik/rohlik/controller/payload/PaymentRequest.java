package com.rohlik.rohlik.controller.payload;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    @Positive
    private Long orderId;

    @Positive
    private BigDecimal paymentAmount;

}
