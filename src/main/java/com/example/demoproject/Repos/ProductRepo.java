package com.example.demoproject.Repos;

import com.example.demoproject.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
