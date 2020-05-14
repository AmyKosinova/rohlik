package com.rohlik.rohlik.mapping;

import com.rohlik.rohlik.domain.OrderedProduct;
import com.rohlik.rohlik.endpoint.payload.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderMapperTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    void shouldMap2ndLvl(){
        ProductDTO dto = new ProductDTO();
        dto.setId(2L);
        dto.setName("vlk");
        OrderedProduct orderedProduct = orderMapper.toOrderedProduct(dto);
        System.out.println(orderedProduct);
    }

}