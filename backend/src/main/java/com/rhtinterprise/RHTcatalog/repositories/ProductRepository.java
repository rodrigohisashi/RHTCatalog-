package com.rhtinterprise.RHTcatalog.repositories;

import com.rhtinterprise.RHTcatalog.entities.Category;
import com.rhtinterprise.RHTcatalog.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cats WHERE "
            + " (COALESCE(:categories) IS NULL OR cats IN :categories) "
            + " AND (LOWER(obj.name) LIKE CONCAT('%',LOWER(:name),'%')"
            + " )"
    )
    Page<Product> find(Pageable pageable, List<Category> categories, String name);
}
