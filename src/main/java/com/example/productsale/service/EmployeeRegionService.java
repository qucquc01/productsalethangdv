package com.example.productsale.service;

import com.example.productsale.dto.EmployeeRegionDTO;

public interface EmployeeRegionService {
    EmployeeRegionDTO create(EmployeeRegionDTO dto);

    EmployeeRegionDTO update(Long id, EmployeeRegionDTO dto);

    EmployeeRegionDTO delete(Long id);

    EmployeeRegionDTO search(EmployeeRegionDTO dto);
}
