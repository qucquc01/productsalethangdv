package com.example.productsale.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "shipper")
@Where(clause = "deleted=0")
@Setter
@Getter
public class ShipperEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "shipper_generator")
    @SequenceGenerator(
            name = "shipper_generator",
            sequenceName = "shipper_sq",
            initialValue = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "post_code")
    private String postCode;

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
