package com.example.productsale.service;

import com.example.productsale.dto.SupplierDTO;

public interface SupplierService {

    SupplierDTO create(SupplierDTO dto);

    SupplierDTO update(Long id, SupplierDTO dto);

    SupplierDTO delete(Long id);

    SupplierDTO search(SupplierDTO dto);
}
