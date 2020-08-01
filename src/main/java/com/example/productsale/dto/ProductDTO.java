package com.example.productsale.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductDTO extends BaseDTO {
    private String name;

    private Long supplierId;

    private Long categoryId;

    private Integer price;

    private Integer discount;

    private String createdName;

    private String updatedName;
}
