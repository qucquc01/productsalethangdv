package com.example.productsale.service;

import com.example.productsale.dto.CategoryDTO;

public interface CategoryService {
    CategoryDTO create(CategoryDTO dto);

    CategoryDTO update(Long id, CategoryDTO dto);

    CategoryDTO delete(Long id);

    CategoryDTO search(CategoryDTO dto);
}
