package com.example.productsale.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Where(clause = "deleted=0")
@Setter
@Getter
public class CategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "category_generator")
    @SequenceGenerator(
            name = "category_generator",
            sequenceName = "category_sq",
            initialValue = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "link")
    private String link;
}
