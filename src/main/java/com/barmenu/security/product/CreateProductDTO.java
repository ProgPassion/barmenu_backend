package com.barmenu.security.product;

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
public class CreateProductDTO {

    @NotBlank(message = "Name is not defined")
    private String name;
    private String description;
    @NotNull(message = "Price is not defined")
    private Float price;
    @NotNull(message = "Category id is not defined")
    private Integer categoryId;
}
