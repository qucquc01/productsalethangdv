package com.example.productsale.service;

import com.example.productsale.dto.OrderDTO;

public interface OrderService {
    OrderDTO create(OrderDTO dto);

    OrderDTO update(Long id, OrderDTO dto);

    OrderDTO delete(Long id);

    OrderDTO search(OrderDTO dto);
}
