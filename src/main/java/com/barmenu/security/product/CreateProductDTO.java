package com.barmenu.security.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {

    @NotBlank(message = "Name is not defined")
    private String name;
    @NotBlank(message = "Description is not defined")
    private String description;
    @NotBlank(message = "Price is not defined")
    private Float price;
    @NotBlank(message = "Category id is not defined")
    private Integer categoryId;
}
