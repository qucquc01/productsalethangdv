package com.example.productsale.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
@Where(clause = "deleted=0")
@Setter
@Getter
public class OrderDetailEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "order_detail_generator")
    @SequenceGenerator(
            name = "order_detail_generator",
            sequenceName = "order_detail_sq",
            initialValue = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", insertable = false,updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private ProductEntity product;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "unit_price")
    private Integer unitPrice;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private OrderEntity order;

    @Column(name = "order_id")
    private Long orderId;
}
