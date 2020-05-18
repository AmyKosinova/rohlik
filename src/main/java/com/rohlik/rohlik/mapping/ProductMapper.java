package com.rohlik.rohlik.mapping;

import com.rohlik.rohlik.domain.OrderedProduct;
import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.endpoint.payload.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MappingConfig.class)
public interface ProductMapper {

    @Mapping(target = "stock", source = "amount")
    Product toProduct(ProductDTO productDTO);

    Set<Product> toProduct(Collection<ProductDTO> productDTO);

}
