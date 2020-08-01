package com.example.productsale.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CompanyDTO extends BaseDTO {

    private String name;

    private String address;

    private String country;

    private String city;

    private String phone;

    private String fax;

    private String createdName;

    private String updatedName;
}
