package com.barmenu.security.category;

import com.barmenu.security.entity.Category;
import com.barmenu.security.product.ProductDTO;
import jakarta.validation.constraints.NotBlank;
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
public class CategoryDTO {
    private Integer id;
    @NotBlank(message = "Name is not defined")
    private String name;
    private String description;
    private Integer userId;
    private Integer rank;
    private List<ProductDTO> products = new ArrayList<>();
    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.rank = category.getRank();
    }
}
