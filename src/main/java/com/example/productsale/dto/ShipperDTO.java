package com.example.productsale.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ShipperDTO extends BaseDTO {

    private String name;

    private String country;

    private String city;

    private String address;

    private String postCode;

    private String phone;

    private String fax;

    private Long companyId;

    private String createdName;

    private String updatedName;
}
