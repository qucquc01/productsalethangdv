package com.example.productsale.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "order")
@Where(clause = "deleted=0")
@Setter
@Getter
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "order_generator")
    @SequenceGenerator(
            name = "order_generator",
            sequenceName = "order_sq",
            initialValue = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private CustomerEntity customer;

    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private EmployeeEntity employee;

    @Column(name = "employee_id")
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipper_id", insertable = false, updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private ShipperEntity shipper;

    @Column(name = "shipper_id")
    private Long shipperId;
}
