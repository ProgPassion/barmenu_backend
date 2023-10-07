package com.barmenu.security.product;

import com.barmenu.security.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditProductDTO {
    private Integer id;
    @NotBlank(message = "Name is not defined")
    private String name;
    private String description;
    @NotNull(message = "Price is not defined")
    private Float price;

    private String categoryName;

    public EditProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }
}
