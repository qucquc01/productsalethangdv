package com.example.productsale.service;

import com.example.productsale.dto.CustomerDTO;

public interface CustomerService {

    CustomerDTO create(CustomerDTO dto);

    CustomerDTO update(Long id, CustomerDTO dto);

    CustomerDTO delete(Long id);

    CustomerDTO search(CustomerDTO dto);
}
