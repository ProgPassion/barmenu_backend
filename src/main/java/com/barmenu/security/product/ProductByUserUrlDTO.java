package com.barmenu.security.product;

import com.barmenu.security.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductByUserUrlDTO {

    private String itemName;
    private Float itemPrice;
    private String itemDescription;

    public ProductByUserUrlDTO(Product product) {
        this.itemName = product.getName();
        this.itemDescription = product.getDescription();
        this.itemPrice = product.getPrice();
    }
}
