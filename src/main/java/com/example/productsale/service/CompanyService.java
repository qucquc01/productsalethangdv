package com.example.productsale.service;

import com.example.productsale.dto.CompanyDTO;

public interface CompanyService {
    CompanyDTO create(CompanyDTO dto);

    CompanyDTO update(Long id, CompanyDTO dto);

    CompanyDTO delete(Long id);

    CompanyDTO search(CompanyDTO dto);
}
