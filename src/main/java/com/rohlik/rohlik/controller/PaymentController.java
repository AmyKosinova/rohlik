package com.rohlik.rohlik.controller;

import com.rohlik.rohlik.controller.payload.OrderResponse;
import com.rohlik.rohlik.controller.payload.PaymentRequest;
import com.rohlik.rohlik.domain.Order;
import com.rohlik.rohlik.mapping.OrderMapper;
import com.rohlik.rohlik.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;


    @PostMapping
    public ResponseEntity<OrderResponse> payOrder(@RequestBody @Validated PaymentRequest paymentRequest) {
        Order order = orderService.registerPayment(paymentRequest);
        return ResponseEntity.ok(orderMapper.toOrderResponse(order));
    }


}
