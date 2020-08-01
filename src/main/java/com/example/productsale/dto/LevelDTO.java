package com.example.productsale.dto;

import com.example.productsale.enums.LevelEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LevelDTO {

    private Boolean active;

    private LevelEnum level;
}
