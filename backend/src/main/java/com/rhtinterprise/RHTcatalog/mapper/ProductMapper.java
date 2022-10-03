package com.rhtinterprise.RHTcatalog.mapper;

import com.rhtinterprise.RHTcatalog.dto.ProductDTO;
import com.rhtinterprise.RHTcatalog.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO productToProductDTO(Product product);


}
