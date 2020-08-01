package com.example.productsale.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Where(clause = "deleted=0")
@Setter
@Getter
public class CustomerEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "customer_generator")
    @SequenceGenerator(
            name = "customer_generator",
            sequenceName = "customer_sq",
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private CompanyEntity company;

    @Column(name = "company_id")
    private Long companyId;
}
