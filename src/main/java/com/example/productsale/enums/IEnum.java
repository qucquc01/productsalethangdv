package com.example.productsale.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface IEnum {

    @JsonValue
    Integer getValue();
}
