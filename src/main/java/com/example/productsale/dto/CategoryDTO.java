package com.example.productsale.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO extends BaseDTO {

    private String name;

    private String description;

    private String link;
}
