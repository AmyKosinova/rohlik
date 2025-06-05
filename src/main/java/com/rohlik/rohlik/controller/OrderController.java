package com.rohlik.rohlik.controller;

import com.rohlik.rohlik.controller.payload.OrderRequest;
import com.rohlik.rohlik.controller.payload.OrderResponse;
import com.rohlik.rohlik.controller.payload.ProductDTO;
import com.rohlik.rohlik.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Validated @RequestBody OrderRequest orderRequest) {
        Set<ProductDTO> missingProducts = orderService.areProductsAvailable(orderRequest.getProducts());
        if (!missingProducts.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(OrderResponse.builder()
                            .missingProducts(missingProducts)
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(orderRequest));
    }

    @DeleteMapping
    public ResponseEntity deleteOrder(@RequestParam Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order " + orderId + " deleted");
    }

    @PutMapping
    public ResponseEntity updateOder(@RequestBody OrderRequest orderRequest) {
        Assert.state(orderRequest.getId() != null && orderRequest.getId() > 0, "ID of order must be provided");
        if (orderRequest.getIncomingPayment() != null
                && BigDecimal.ZERO.compareTo(orderRequest.getIncomingPayment()) < 0) {
            orderService.registerPayment(orderRequest);
            return ResponseEntity
                    .ok("Order payed.");
        }
        throw new UnsupportedOperationException("Only positive money payment is possible");
    }

    @GetMapping
    public String doMagic() {


        return "maagic";
    }


}
