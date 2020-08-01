package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.OrderEntity;
import com.example.productsale.dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    OrderEntity getById(Long id);

    @Query(value = "select o from OrderEntity o" +
            " where 1 = 1" +
            " and (o.customerId like :#{#dto.customerId} or :#{#dto.customerId} is null )" +
            " and (o.employeeId like :#{#dto.employeeId} or :#{#dto.employeeId} is null )" +
            " and (o.shipperId like :#{#dto.shipperId} or :#{#dto.shipperId} is null )")
    OrderEntity search(OrderDTO dto);
}
