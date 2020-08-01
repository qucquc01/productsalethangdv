package com.example.productsale.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EmployeeDTO extends BaseDTO {

    private String name;

    private String country;

    private String city;

    private String link;

    private String createdName;

    private String updatedName;

    private Long employeeRegionId;
}
