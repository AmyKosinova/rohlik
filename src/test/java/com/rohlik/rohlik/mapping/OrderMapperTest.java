package com.rohlik.rohlik.mapping;

import com.rohlik.rohlik.ContextTest;
import com.rohlik.rohlik.controller.payload.ProductDTO;
import com.rohlik.rohlik.domain.OrderedProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderMapperTest implements ContextTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    void shouldMap2ndLvl(){
        ProductDTO dto = new ProductDTO();
        dto.setId(2L);
        dto.setName("vlk");
        OrderedProduct orderedProduct = orderMapper.toOrderedProduct(dto);

        assertNotNull(orderedProduct.getOriginalProduct());
    }

}