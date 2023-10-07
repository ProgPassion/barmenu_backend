package com.barmenu.security.product;

import com.barmenu.security.category.CategoryByUserUrlDTO;
import com.barmenu.security.category.CategoryDTO;
import com.barmenu.security.entity.Product;
import com.barmenu.security.exception.category.CategoryIdDoesntExistsException;
import com.barmenu.security.exception.category.CategoryNameExistsException;
import com.barmenu.security.exception.product.ProductNameExistsException;
import com.barmenu.security.exception.product.ProductNotFoundException;
import com.barmenu.security.exception.url.UrlNotFoundException;
import com.barmenu.security.service.ProductService;
import com.barmenu.security.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> get(Authentication auth) {
        var userDetails = (User) auth.getPrincipal();
        var products = service.getProducts(userDetails.getId());
        return ResponseEntity.ok(products);
    }



    @GetMapping("/by-category")
    public ResponseEntity<List<ProductDTO>> getByCategoryName(@RequestParam String category, Authentication auth) throws CategoryNameExistsException {
        var userDetails = (User) auth.getPrincipal();
        var products = service.getProductsByCategory(category, userDetails.getId());
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<CreateProductDTO> addProduct(@Valid @RequestBody CreateProductDTO dto, Authentication auth) throws ProductNameExistsException, CategoryIdDoesntExistsException {
        var userDetails = (User) auth.getPrincipal();
        var product = service.addProduct(userDetails.getId(), dto);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/edit")
    public ResponseEntity<ProductDTO> editProduct(@Valid @RequestBody EditProductDTO dto, Authentication auth) throws ProductNameExistsException, ProductNotFoundException {
        var userDetails = (User) auth.getPrincipal();
        var product = service.editProduct(dto, userDetails.getId());
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id, Authentication auth) throws ProductNotFoundException {
        var userDetails = (User) auth.getPrincipal();
        service.removeProduct(id, userDetails.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
