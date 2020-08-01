package com.example.productsale.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderDTO extends BaseDTO {

    private Long customerId;

    private Long employeeId;

    private Long shipperId;

    private String createdName;

    private String updatedName;
}
