package com.barmenu.security.repo;

import com.barmenu.security.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsCategoryByName(String name);
    boolean existsCategoryById(Integer id);

    @Query("SELECT COUNT(c) FROM Category c WHERE c.name = ?1 and c.id <> ?2")
    Integer existsCategoryByNameWithDifferentId(String name, Integer id);

    //@Query("SELECT c FROM Category c join fetch c.products")
    //List<Category> getAll();
}
