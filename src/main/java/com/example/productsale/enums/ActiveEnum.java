package com.example.productsale.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum  ActiveEnum implements IEnum {
    ACTIVE(0), INACTIVE(1);

    private Integer value;

    private ActiveEnum(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @JsonCreator
    static ActiveEnum fromValue(int value){
        return Stream.of(ActiveEnum.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
