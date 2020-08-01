package com.example.productsale.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EmployeeRegionDTO extends BaseDTO {

    private Long regionId;

    private String createdName;

    private String updatedName;

}
