package com.barmenu.security.service;

import com.barmenu.security.category.CategoryDTO;
import com.barmenu.security.entity.Category;
import com.barmenu.security.entity.Product;
import com.barmenu.security.exception.category.CategoryIdDoesntExistsException;
import com.barmenu.security.exception.product.ProductNameExistsException;
import com.barmenu.security.product.CreateProductDTO;
import com.barmenu.security.product.ProductDTO;
import com.barmenu.security.repo.CategoryRepository;
import com.barmenu.security.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public CreateProductDTO addProduct(CreateProductDTO dto) throws ProductNameExistsException, CategoryIdDoesntExistsException {

        if(productRepo.existsProductByName(dto.getName())) {
            throw new ProductNameExistsException();
        }
        if(!categoryRepo.existsCategoryById(dto.getCategoryId())) {
            throw new CategoryIdDoesntExistsException();
        }

        var product = new Product();
        product.setCategory(new Category(dto.getCategoryId()));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        productRepo.save(product);
        return dto;
    }

    public List<CategoryDTO> getProducts() {
        List<Product> products = productRepo.findAll();
        Map<Category, List<Product>> categoryProductMap = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for(Map.Entry<Category, List<Product>> entry: categoryProductMap.entrySet()) {
            Category category = entry.getKey();
            List<Product> categoryProducts = entry.getValue();

            List<ProductDTO> productDTOs = categoryProducts
                    .stream()
                    .map(ProductDTO::new)
                    .toList();
            CategoryDTO categoryDTO = new CategoryDTO(category);
            categoryDTO.setProducts(productDTOs);

            categoryDTOs.add(categoryDTO);
        }

        return categoryDTOs;
    }
}
