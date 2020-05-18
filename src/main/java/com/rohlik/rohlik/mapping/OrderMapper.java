package com.rohlik.rohlik.mapping;

import com.rohlik.rohlik.domain.OrderedProduct;
import com.rohlik.rohlik.domain.Product;
import com.rohlik.rohlik.endpoint.payload.ProductDTO;
import com.sun.corba.se.impl.util.ORBProperties;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MappingConfig.class)
public interface OrderMapper {

    @Mapping(target = "originalProduct.name", source = "name")
    @Mapping(target = "originalProduct.id", source = "id")
    @Mapping(target = "id", ignore = true)
    OrderedProduct toOrderedProduct(ProductDTO productDTO);

    Set<OrderedProduct> toOrderedProduct(Collection<ProductDTO> productDTO);

}
