package com.rohlik.rohlik.mapping;

import com.rohlik.rohlik.controller.payload.ProductDTO;
import com.rohlik.rohlik.domain.OrderedProduct;
import com.rohlik.rohlik.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper(config = MappingConfig.class)
public interface ProductMapper {

    @Mapping(target = "stock", source = "amount")
    Product toProduct(ProductDTO productDTO);

    @Mapping(target = "amount", source = "stock")
    ProductDTO toDto(Product product);

    @Mapping(target = "name", source = "originalProduct.name")
    ProductDTO toProductDTO(OrderedProduct product);

    Set<Product> toProduct(Collection<ProductDTO> productDTO);

    List<ProductDTO> toDto(List<Product> employees);

    void update(ProductDTO productDTO, @MappingTarget Product product);
}
