package com.example.productsale.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "employee_region")
@Where(clause = "deleted=0")
@Setter
@Getter
public class EmployeeRegionEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "employee_region_generator")
    @SequenceGenerator(
            name = "employee_region_generator",
            sequenceName = "employee_region_sq",
            initialValue = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private RegionEntity region;

    @Column(name = "region_id")
    private Long regionId;
}
