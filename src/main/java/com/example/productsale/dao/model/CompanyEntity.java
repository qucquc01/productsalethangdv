package com.example.productsale.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "company")
@Where(clause = "deleted=0")
@Setter
@Getter
public class CompanyEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "company_generator")
    @SequenceGenerator(
            name = "company_generator",
            sequenceName = "companyr_sq",
            initialValue = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;
}
