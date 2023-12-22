package com.barmenu.security.category;

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
public class MenuCompleteDTO {
    private String businessName;
    private List<MenuItemsDTO> menuItems = new ArrayList<>();
}
