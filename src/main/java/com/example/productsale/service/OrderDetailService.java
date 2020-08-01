package com.example.productsale.service;

import com.example.productsale.dto.OrderDetailDTO;

public interface OrderDetailService {

    OrderDetailDTO create(OrderDetailDTO dto);

    OrderDetailDTO update(Long id, OrderDetailDTO dto);

    OrderDetailDTO delete(Long id);

    OrderDetailDTO search(OrderDetailDTO dto);
}
