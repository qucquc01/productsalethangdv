package com.example.productsale.service;

import com.example.productsale.dto.EmployeeDTO;

public interface EmployeeService {

    EmployeeDTO create(EmployeeDTO dto);

    EmployeeDTO update(Long id, EmployeeDTO dto);

    EmployeeDTO delete(Long id);

    EmployeeDTO search(EmployeeDTO dto);
}
