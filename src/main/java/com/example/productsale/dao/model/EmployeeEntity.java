package com.example.productsale.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@Where(clause = "deleted=0")
@Setter
@Getter
public class EmployeeEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "employee_generator")
    @SequenceGenerator(
            name = "employee_generator",
            sequenceName = "employee_sq",
            initialValue = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "link")
    private String link;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_region_id", insertable = false, updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private EmployeeRegionEntity employeeRegion;

    @Column(name = "employee_region_id")
    private Long employeeRegionId;
}
