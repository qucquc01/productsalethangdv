package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.OrderDetailEntity;
import com.example.productsale.dto.OrderDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    OrderDetailEntity getById(Long id);

    @Query(value = "select o from OrderDetailEntity o" +
            " where 1 = 1" +
            " and (o.productId = :#{#dto.productId} or :#{#dto.productId} is null)" +
            " and (o.unitPrice = :#{#dto.unitPrice} or :#{#dto.unitPrice} is null )" +
            " and (o.discount = :#{#dto.discount} or :#{#dto.discount} is null )" +
            " and (o.quantity like :#{#dto.quantity} or :#{#dto.quantity} is null )" +
            " and (o.orderId = :#{#dto.orderId} or :#{#dto.orderId} is null )")
    OrderDetailEntity search(OrderDetailDTO dto);
}
