package com.barmenu.security.category;

import com.barmenu.security.entity.Category;
import com.barmenu.security.exception.category.CategoryNameExistsException;
import com.barmenu.security.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> get() {
        var categories = service.getCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@Valid @RequestBody CategoryDTO dto) throws CategoryNameExistsException {
        var category = service.addCategory(dto);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/edit")
    public ResponseEntity<CategoryDTO> edit(@Valid @RequestBody CategoryDTO dto) throws CategoryNameExistsException {
        var category = service.editCategory(dto);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.removeCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
