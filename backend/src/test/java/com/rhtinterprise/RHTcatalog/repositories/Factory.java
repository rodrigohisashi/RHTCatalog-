package com.rhtinterprise.RHTcatalog.repositories;

import com.rhtinterprise.RHTcatalog.dto.ProductDTO;
import com.rhtinterprise.RHTcatalog.entities.Category;
import com.rhtinterprise.RHTcatalog.entities.Product;

import java.time.Instant;

public class Factory {
    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "http://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
        product.getCategories().add(new Category(2L, "Eletronics"));
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
