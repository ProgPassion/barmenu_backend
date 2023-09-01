package com.barmenu.security.product;

import com.barmenu.security.category.CategoryDTO;
import com.barmenu.security.entity.Product;
import com.barmenu.security.exception.category.CategoryIdDoesntExistsException;
import com.barmenu.security.exception.product.ProductNameExistsException;
import com.barmenu.security.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> get() {
        var products = service.getProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<CreateProductDTO> addProduct(@Valid @RequestBody CreateProductDTO dto) throws ProductNameExistsException, CategoryIdDoesntExistsException {
        var product = service.addProduct(dto);
        return ResponseEntity.ok(product);
    }
}
