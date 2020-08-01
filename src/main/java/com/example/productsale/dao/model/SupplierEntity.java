package com.example.productsale.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "supplier")
@Where(clause = "deleted=0")
@Setter
@Getter
public class SupplierEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "supplier_generator")
    @SequenceGenerator(
            name = "supplier_generator",
            sequenceName = "supplier_sq",
            initialValue = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;
}
