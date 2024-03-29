package com.barmenu.security.repo;

import com.barmenu.security.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsCategoryByName(String name);
    boolean existsCategoryByNameAndUser_Id(String name, Integer userId);
    boolean existsCategoryById(Integer id);
    Category getCategoriesById(Integer id);
    List<Category> findAllByUser_Id(Integer userId);

    List<Category> findAllByUser_Url(String url);

    //Check whether the name the user is trying to use is used somewhere else in his category
    //list
    @Query("SELECT COUNT(c) FROM Category c WHERE c.name = ?1 and c.user.id = ?2 and c.id <> ?3")
    Integer existsCategoryByNameWithDifferentId(String name, Integer userId, Integer id);

    @Query("SELECT MIN(c.id) FROM Category c WHERE c.user.id = ?1")
    Integer getFirstCategoryId(Integer id);

    @Query("SELECT c FROM Category c WHERE c.name = ?1 AND c.user.id = ?2")
    Category findCategoryByNameAndUserId(String name, Integer userId);
    //@Query("SELECT c FROM Category c join fetch c.products")
    //List<Category> getAll();

    @Query("SELECT c FROM Category c WHERE c.user.id = :userId order by c.rank")
    List<Category> getRankedCategoriesByUserId(
            @Param("userId") Integer userId
    );

    @Query("SELECT c FROM Category c WHERE c.user.url = :userUrl order by c.rank")
    List<Category> getRankedCategoriesByUserUrl(
            @Param("userUrl") String userUrl
    );

    @Query("SELECT MAX(c.rank) FROM Category c WHERE c.user.id = :userId")
    Integer getHighestRankNum(
            @Param("userId") Integer userId
    );
}
