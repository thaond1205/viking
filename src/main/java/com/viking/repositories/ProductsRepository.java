package com.viking.repositories;

import com.viking.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Product, Integer> {
    Product save(Product product);
    List<Product> findAll();
}
