package com.rhtinterprise.RHTcatalog.tests;

import com.rhtinterprise.RHTcatalog.dto.ProductDTO;
import com.rhtinterprise.RHTcatalog.entities.Category;
import com.rhtinterprise.RHTcatalog.entities.Product;

import java.time.LocalDateTime;

public class Factory {
    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "http://img.com/img.png", LocalDateTime.of(2012, 02, 12, 2 ,2, 2));
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
    public static Category createCategory() {
        return new Category(2L, "Eletronics");
    }

}
