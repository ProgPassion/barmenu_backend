package com.barmenu.security.category;

import com.barmenu.security.entity.Category;
import com.barmenu.security.exception.category.CategoryIdDoesntExistsException;
import com.barmenu.security.exception.category.CategoryNameExistsException;
import com.barmenu.security.exception.category.DefaultCategoryException;
import com.barmenu.security.service.CategoryService;
import com.barmenu.security.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> get(Authentication auth) {
        var details = (User) auth.getPrincipal();
        var categories = service.getCategories(details.getId());
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDTO> add(@Valid @RequestBody CategoryDTO dto, Authentication auth) throws CategoryNameExistsException {
        var details = (User) auth.getPrincipal();
        dto.setUserId(details.getId());
        service.addCategory(dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/edit")
    public ResponseEntity<CategoryDTO> edit(@Valid @RequestBody CategoryDTO dto, Authentication auth) throws CategoryNameExistsException, CategoryIdDoesntExistsException, DefaultCategoryException {
        var details = (User) auth.getPrincipal();
        dto.setUserId(details.getId());
        var category = service.editCategory(dto);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/changerank")
    public ResponseEntity<Void> changeRank(@RequestBody List<CategoryRankIdDTO> categoryChangeRank, Authentication auth) throws CategoryIdDoesntExistsException {
        var details = (User) auth.getPrincipal();
        service.changeCategoryRank(categoryChangeRank, details.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id, Authentication auth) throws CategoryIdDoesntExistsException, DefaultCategoryException {
        var details = (User) auth.getPrincipal();
        service.removeCategory(id, details.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
