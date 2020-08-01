package com.example.productsale.dao.model;

import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
public abstract class BaseEntity extends Audit implements Serializable {
    @Column(name = "deleted", nullable = false)
    @Audited
    private Long deleted = 0L;

    public abstract Long getId();

    public abstract void setId(Long id);

    public BaseEntity(Long id) {
        setId(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof BaseEntity)) {
            return false;
        }

        BaseEntity entity = (BaseEntity) obj;

        if (entity.getId() == null) {
            return false;
        }

        return entity.getId().equals(getId());
    }
}
