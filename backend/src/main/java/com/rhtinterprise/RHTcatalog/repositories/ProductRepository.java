package com.rhtinterprise.RHTcatalog.repositories;

import com.rhtinterprise.RHTcatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
