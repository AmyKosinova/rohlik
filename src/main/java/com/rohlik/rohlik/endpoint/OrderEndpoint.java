package com.rohlik.rohlik.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.endpoint.payload.OrderRequest;
import com.rohlik.rohlik.endpoint.payload.OrderResponse;
import com.rohlik.rohlik.endpoint.payload.ProductDTO;
import com.rohlik.rohlik.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("order/")
@AllArgsConstructor
public class OrderEndpoint {

    private final OrderService orderService;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping
    @SneakyThrows
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        Set<ProductDTO> missingProducts = orderService.areProductsAvailable(orderRequest.getProducts());
        if (!missingProducts.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(OrderResponse.builder()
                            .missingProducts(missingProducts)
                            .build());
        }
        BigDecimal orderPrice = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderResponse.builder().totalPrice(orderPrice)
                        .build());
    }

    @DeleteMapping
    public void deleteOrder(Long orderId) {

    }

    @PutMapping
    public void updateOder(Long orderId) {

    }

    @GetMapping
    public String doMagic() {


        return "maagic";
    }


}
