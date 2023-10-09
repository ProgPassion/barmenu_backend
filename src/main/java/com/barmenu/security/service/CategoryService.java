package com.barmenu.security.service;

import com.barmenu.security.category.CategoryDTO;
import com.barmenu.security.category.CategoryRankIdDTO;
import com.barmenu.security.entity.Category;
import com.barmenu.security.entity.Product;
import com.barmenu.security.exception.category.CategoryIdDoesntExistsException;
import com.barmenu.security.exception.category.CategoryNameExistsException;
import com.barmenu.security.exception.category.DefaultCategoryException;
import com.barmenu.security.repo.CategoryRepository;
import com.barmenu.security.repo.ProductRepository;
import com.barmenu.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repo;
    private final ProductRepository productRepo;

    public void addDefaultCategory(User user) {
        var category = new Category();
        category.setName("Others");
        category.setDescription("Other products category");
        category.setUser(user);
        repo.save(category);
    }

    public CategoryDTO addCategory(CategoryDTO dto) throws CategoryNameExistsException {
        if (repo.existsCategoryByNameAndUser_Id(dto.getName(), dto.getUserId())) {
            throw new CategoryNameExistsException();
        }
        var category = new Category();
        var user = new User();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        user.setId(dto.getUserId());
        category.setUser(user);
        repo.save(category);
        return dto;
    }

    public List<CategoryDTO> getCategories(Integer userId) {
        List<Category> categories = repo.getRankedCategoriesByUserId(userId);
        return categories.stream()
                .map(CategoryDTO::new)
                .toList();
    }

    public List<CategoryDTO> getCategories(String url) {
        List<Category> categories = repo.getRankedCategoriesByUserUrl(url);
        return categories.stream()
                .map(CategoryDTO::new)
                .toList();
    }

    public CategoryDTO editCategory(CategoryDTO dto) throws CategoryNameExistsException, CategoryIdDoesntExistsException, DefaultCategoryException {
        if(repo.getFirstCategoryId(dto.getUserId()).equals(dto.getId())) {
            throw new DefaultCategoryException();
        }
        Category category = repo.getCategoriesById(dto.getId());

        if(category == null) {
            throw new CategoryIdDoesntExistsException();
        }
        //Check if category userId is the same with the userId that requested the deletion
        //If not throw an exception
        if(!category.getUser().getId().equals(dto.getUserId())) {
            throw new CategoryIdDoesntExistsException();
        }

        // Check if you are updating the category with a name that already exists in db
        if(repo.existsCategoryByNameWithDifferentId(dto.getName(), dto.getUserId(), dto.getId()) > 0) {
            throw new CategoryNameExistsException();
        }

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        repo.save(category);
        return dto;
    }

    public void changeCategoryRank(List<CategoryRankIdDTO> categoryRankIdDTOList, Integer userId) throws CategoryIdDoesntExistsException {
        for(var dto: categoryRankIdDTOList) {
            var category = repo.getCategoriesById(dto.getId());
            if(!category.getUser().getId().equals(userId)) {
                throw new CategoryIdDoesntExistsException();
            }
            category.setRank(dto.getRank());
            repo.save(category);
        }
    }

    public void removeCategory(Integer id, Integer userId) throws CategoryIdDoesntExistsException, DefaultCategoryException {
        Integer defaultCategoryId = repo.getFirstCategoryId(userId);
        if(defaultCategoryId == id) {
            throw new DefaultCategoryException();
        }

        Category category = repo.getCategoriesById(id);
        if(category == null) {
            throw new CategoryIdDoesntExistsException();
        }
        //Check if category userId is the same with the userId that requested the deletion
        //If not throw an exception
        if(!category.getUser().getId().equals(userId)) {
            throw new CategoryIdDoesntExistsException();
        }

        List<Product> products = productRepo.findProductsByUserIdAndCategoryId(userId, id);
        Category defaultCategory = new Category();
        defaultCategory.setId(defaultCategoryId);

        for(var product: products) {
            product.setCategory(defaultCategory);
            productRepo.save(product);
        }
        repo.deleteById(id);
    }
}
