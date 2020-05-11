package com.rohlik.rohlik.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.domain.repository.ProductRepository;
import com.rohlik.rohlik.endpoint.request.OrderDTO;
import com.rohlik.rohlik.endpoint.request.ProductDTO;
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

import javax.naming.ldap.PagedResultsResponseControl;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("order/")
@AllArgsConstructor
public class OrderEndpoint {

    private final OrderService orderService;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping
    @SneakyThrows
    public ResponseEntity createOrder(@RequestBody OrderDTO orderDTO) {
        Map<ProductDTO, Integer> missingProducts = orderService.areProductsAvailable(orderDTO.getProducts());
        if (!missingProducts.isEmpty()) {
            return ResponseEntity.badRequest()
//                    .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(missingProducts))
                    .body(missingProducts)
                    ;
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
//        orderService.createOrder();
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
