package com.example.productsale.dto;

import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BaseDTO implements Serializable {

    private Long id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof BaseDTO)) {
            return false;
        }

        BaseDTO dto = (BaseDTO) obj;

        if (dto.getId() == null) {
            return false;
        }

        return dto.getId().equals(getId());
    }

}
