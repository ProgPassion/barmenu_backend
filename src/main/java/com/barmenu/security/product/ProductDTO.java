package com.barmenu.security.product;

import com.barmenu.security.entity.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer id;
    @NotBlank(message = "Name is not defined")
    private String name;
    @NotBlank(message = "Description is not defined")
    private String description;
    @NotBlank(message = "Price is not defined")
    private Float price;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }
}
