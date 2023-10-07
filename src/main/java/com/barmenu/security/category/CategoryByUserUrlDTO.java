package com.barmenu.security.category;

import com.barmenu.security.entity.Category;
import com.barmenu.security.product.ProductByUserUrlDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryByUserUrlDTO {

    private String category;
    private String description;
    private List<ProductByUserUrlDTO> items = new ArrayList<>();

    public CategoryByUserUrlDTO(Category category) {
        this.category = category.getName();
        this.description = category.getDescription();
    }
}
