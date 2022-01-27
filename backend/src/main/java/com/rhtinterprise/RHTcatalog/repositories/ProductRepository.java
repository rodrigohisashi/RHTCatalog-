package com.rhtinterprise.RHTcatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhtinterprise.RHTcatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
