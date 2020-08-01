package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.ProductEntity;
import com.example.productsale.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity findFirstByNameIgnoreCase(String name);

    ProductEntity getById(Long id);

    @Query(value = "select p from ProductEntity p" +
            " where 1 = 1" +
            " and (p.name like :#{#dto.name} or :#{#dto.name} is null )" +
            " and (p.supplierId = :#{#dto.supplierId} or :#{#dto.supplierId} is null)" +
            " and (p.categoryId = :#{#dto.categoryId} or :#{#dto.categoryId} is null )" +
            " and (p.price = :#{#dto.price} or :#{#dto.price} is null )" +
            " and (p.discount like :#{#dto.discount} or :#{#dto.discount} is null )")
    ProductEntity search(ProductDTO dto);
}
