package com.rohlik.rohlik.mapping;

import com.rohlik.rohlik.controller.payload.OrderResponse;
import com.rohlik.rohlik.controller.payload.ProductDTO;
import com.rohlik.rohlik.domain.Order;
import com.rohlik.rohlik.domain.OrderedProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.Set;

@Mapper(config = MappingConfig.class, uses = {ProductMapper.class})
public interface OrderMapper {

    @Mapping(target = "originalProduct.name", source = "name")
    @Mapping(target = "originalProduct.id", source = "id")
    @Mapping(target = "id", ignore = true)
    OrderedProduct toOrderedProduct(ProductDTO productDTO);

    Set<OrderedProduct> toOrderedProduct(Collection<ProductDTO> productDTO);

    @Mapping(target = "orderProducts", source = "products")
    OrderResponse toOrderResponse(Order orderedProduct);

}
