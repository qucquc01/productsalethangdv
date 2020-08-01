package com.example.productsale.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderDetailDTO extends BaseDTO {
    private Long productId;

    private Integer unitPrice;

    private Integer discount;

    private Integer quantity;

    private String createdName;

    private String updatedName;

    private Long orderId;
}
