package com.barmenu.security.service;

import com.barmenu.security.category.MenuCompleteDTO;
import com.barmenu.security.category.MenuItemsDTO;
import com.barmenu.security.category.CategoryDTO;
import com.barmenu.security.entity.Category;
import com.barmenu.security.entity.Product;
import com.barmenu.security.exception.category.CategoryIdDoesntExistsException;
import com.barmenu.security.exception.category.CategoryNameExistsException;
import com.barmenu.security.exception.product.ProductNameExistsException;
import com.barmenu.security.exception.product.ProductNotFoundException;
import com.barmenu.security.exception.url.UrlNotFoundException;
import com.barmenu.security.product.CreateProductDTO;
import com.barmenu.security.product.EditProductDTO;
import com.barmenu.security.product.ProductByUserUrlDTO;
import com.barmenu.security.product.ProductDTO;
import com.barmenu.security.repo.CategoryRepository;
import com.barmenu.security.repo.ProductRepository;
import com.barmenu.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final UserRepository userRepo;

    public CreateProductDTO addProduct(Integer userId, CreateProductDTO dto) throws ProductNameExistsException, CategoryIdDoesntExistsException {

        if(!productRepo.existsProductByName(userId, dto.getName()).isEmpty()) {
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

    public List<CategoryDTO> getProducts(Integer userId) {
        List<Product> products = productRepo.findProductsByUserId(userId);

        Map<Category, List<Product>> categoryProductMap = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

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

    public MenuCompleteDTO getMenuComplete(String url) throws UrlNotFoundException {
        String businessName = getBusinessNameByUserUrl(url);
        List<MenuItemsDTO> menuItems = getProductsByUserUrl(url);
        return new MenuCompleteDTO(businessName, menuItems);
    }

    private String getBusinessNameByUserUrl(String url) throws UrlNotFoundException {
        if(!userRepo.findByUrl(url).isPresent()) {
            throw new UrlNotFoundException();
        }
        return userRepo.findBusinessNameByUrl(url);
    }
    private List<MenuItemsDTO> getProductsByUserUrl(String url) throws UrlNotFoundException {
        if(!userRepo.findByUrl(url).isPresent()) {
            throw new UrlNotFoundException();
        }
        List<Product> products = productRepo.findProductsByUserUrl(url);

        Map<Category, List<Product>> categoryProductMap = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<MenuItemsDTO> categoryDTOs = new ArrayList<>();

        for(Map.Entry<Category, List<Product>> entry: categoryProductMap.entrySet()) {
            Category category = entry.getKey();
            List<Product> categoryProducts = entry.getValue();

            List<ProductByUserUrlDTO> productDTOs = categoryProducts
                    .stream()
                    .map(ProductByUserUrlDTO::new)
                    .toList();
            MenuItemsDTO categoryDTO = new MenuItemsDTO(category);
            categoryDTO.setItems(productDTOs);
            categoryDTOs.add(categoryDTO);
        }

        return categoryDTOs;
    }

    public List<ProductDTO> getProductsByCategory(String categoryName, Integer userId) throws CategoryNameExistsException {
        if(!categoryRepo.existsCategoryByName(categoryName)) {
            throw new CategoryNameExistsException();
        }
        List<Product> products = productRepo.findProductsByUserIdAndCategoryName(userId, categoryName);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product: products) {
            productDTOS.add(new ProductDTO(product));
        }
        return productDTOS;
    }

    public ProductDTO editProduct(EditProductDTO dto, Integer userId) throws ProductNameExistsException, ProductNotFoundException {
        if(productRepo.doesProductBelongToUser(dto.getId(), userId) < 1) {
            throw new ProductNotFoundException();
        }
        // Check if you are updating the product with a name that already exists in db
        List<Product> products = productRepo.existsProductByNameWithDifferentId(userId, dto.getId(), dto.getName());
        if(!products.isEmpty()) {
            throw new ProductNameExistsException();
        }

        var product = productRepo.findById(dto.getId())
                .orElseThrow(() -> new ProductNotFoundException());

        var category = categoryRepo.findCategoryByNameAndUserId(dto.getCategoryName(), userId);


        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(category);

        productRepo.save(product);
        return new ProductDTO(product);
    }

    public void removeProduct(Integer id, Integer userId) throws ProductNotFoundException {
        if(productRepo.doesProductBelongToUser(id, userId) < 1) {
            throw new ProductNotFoundException();
        }
        productRepo.deleteById(id);
    }
}
