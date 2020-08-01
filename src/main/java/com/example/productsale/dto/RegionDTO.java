package com.example.productsale.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RegionDTO extends BaseDTO {

    private String description;

    private String createdName;

    private String updatedName;
}
