package com.barmenu.security.repo;

import com.barmenu.security.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.category c " +
            "JOIN c.user u " +
            "WHERE u.id = :userId " +
            "AND p.name = :productName")
    List<Product> existsProductByName(
            @Param("userId") Integer userId,
            @Param("productName") String productName
    );
    List<Product> findProductsByCategory_Name(String category);
    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.category c " +
            "WHERE c.user.id = :userId " +
            "AND p.id <> :productId " +
            "AND p.name = :productName")
    List<Product> existsProductByNameWithDifferentId(
          @Param("userId") Integer userId,
          @Param("productId") Integer productId,
          @Param("productName") String productName
    );
    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.category c " +
            "JOIN c.user u " +
            "WHERE u.id = :userId " +
            "AND c.name = :categoryName")
    List<Product> findProductsByUserIdAndCategoryName(
            @Param("userId") Integer userId,
            @Param("categoryName") String categoryName
    );

    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.category c " +
            "JOIN c.user u " +
            "WHERE u.id = :userId " +
            "AND c.id = :categoryId")
    List<Product> findProductsByUserIdAndCategoryId(
            @Param("userId") Integer userId,
            @Param("categoryId") Integer categoryId
    );


    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.category c " +
            "JOIN c.user u " +
            "WHERE u.id = :userId ")
    List<Product> findProductsByUserId(
            @Param("userId") Integer userId
    );

    @Query("SELECT COUNT(p) FROM Product p " +
            "WHERE p.id = :productId " +
            "AND p.category.user.id = :userId")
    Integer doesProductBelongToUser(
            @Param("productId") Integer productId,
            @Param("userId") Integer userId
    );

    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.category c " +
            "JOIN c.user u " +
            "WHERE u.url = :userUrl")
    List<Product> findProductsByUserUrl(
            @Param("userUrl") String userUrl
    );
}
