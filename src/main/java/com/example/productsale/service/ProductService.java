package com.example.productsale.service;

import com.example.productsale.dto.ProductDTO;

public interface ProductService {
    ProductDTO create(ProductDTO dto);

    ProductDTO update(Long id, ProductDTO dto);

    ProductDTO delete(Long id);

    ProductDTO search(ProductDTO dto);
}
