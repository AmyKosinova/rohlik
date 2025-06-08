package com.rohlik.rohlik.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.rohlik.rohlik.RohlikApplication;
import com.rohlik.rohlik.controller.payload.OrderRequest;
import com.rohlik.rohlik.controller.payload.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = RohlikApplication.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void shouldCreateOrder() throws Exception {
        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                OrderRequest.builder()
                                        .products(ImmutableSet.of(ProductDTO.builder()
                                                .id(2L)
                                                .amount(3L)
                                                .name("prodName")
                                                .build()))
                                        .build()
                        )))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldVerifyMissingProducts() throws Exception {
        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                OrderRequest.builder()
                                        .products(ImmutableSet.of(ProductDTO.builder()
                                                .id(1L)
                                                .amount(300L)
                                                .name("prodName")
                                                .build()))
                                        .build()
                        )))
                .andExpect(ResultMatcher.matchAll(
                        status().isBadRequest(),
                        MockMvcResultMatchers.content().json("{\"missingProducts\":[{\"id\":1,\"amount\":293,\"name\":\"milk\",\"unitPrice\":22.1000}]}")
                ));
    }

}