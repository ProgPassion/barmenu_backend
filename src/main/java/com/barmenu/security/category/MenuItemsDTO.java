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
public class MenuItemsDTO {

    private String category;
    private String description;
    private List<ProductByUserUrlDTO> items = new ArrayList<>();

    public MenuItemsDTO(Category category) {
        this.category = category.getName();
        this.description = category.getDescription();
    }
}
