package com.barmenu.security.public_controller;

import com.barmenu.security.category.CategoryByUserUrlDTO;
import com.barmenu.security.exception.url.UrlNotFoundException;
import com.barmenu.security.service.ProductService;
import com.barmenu.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final ProductService service;

    @GetMapping("{url}")
    public ResponseEntity<List<CategoryByUserUrlDTO>> getByUserUrl(@PathVariable String url) throws UrlNotFoundException {
        var products = service.getProductsByUserUrl(url);
        return ResponseEntity.ok(products);
    }
}
