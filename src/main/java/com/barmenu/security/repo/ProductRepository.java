package com.barmenu.security.repo;


import com.barmenu.security.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsProductByName(String name);
}
