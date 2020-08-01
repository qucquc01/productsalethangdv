package com.example.productsale.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "region")
@Where(clause = "deleted=0")
@Setter
@Getter
public class RegionEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "region_generator")
    @SequenceGenerator(
            name = "region_generator",
            sequenceName = "region_sq",
            initialValue = 1
    )
    private Long id;

    @Column(name = "description")
    private String description;
}
