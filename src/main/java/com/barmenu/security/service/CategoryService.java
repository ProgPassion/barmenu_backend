package com.barmenu.security.service;

import com.barmenu.security.category.CategoryDTO;
import com.barmenu.security.entity.Category;
import com.barmenu.security.exception.category.CategoryNameExistsException;
import com.barmenu.security.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repo;

    public Category addCategory(CategoryDTO dto) throws CategoryNameExistsException {
        if (repo.existsCategoryByName(dto.getName())) {
            throw new CategoryNameExistsException();
        }
        var category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return repo.save(category);
    }

    public List<CategoryDTO> getCategories() {
        List<Category> categories = repo.findAll();
        return categories.stream()
                .map(CategoryDTO::new)
                .toList();
    }

    public CategoryDTO editCategory(CategoryDTO dto) throws CategoryNameExistsException {
        // Check if you are updating the category with a name that already exists in db
        if(repo.existsCategoryByNameWithDifferentId(dto.getName(), dto.getId()) > 0) {
            throw new CategoryNameExistsException();
        }
        var category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        repo.save(category);
        return dto;
    }

    public void removeCategory(Integer id) {
        repo.deleteById(id);
    }
}
