package com.example.productsale.dto;

import com.example.productsale.enums.ActiveEnum;
import com.example.productsale.enums.LevelEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MenuDTO extends BaseDTO {
    private String name;

    private Integer location;

    private String link;

    private Long parentId;

    private LevelEnum level;

    private List<MenuDTO> children;

    private String createdName;

    private String updatedName;

    private String subMenu;

    private Boolean active;
}
