package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.CategoryEntity;
import com.example.productsale.dto.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findFirstByNameIgnoreCase(String name);

    @Query(value = "select c from CategoryEntity c" +
            " where 1 = 1" +
            " and (c.name like :#{#dto.name} or :#{#dto.name} is null )")
    CategoryEntity search(CategoryDTO dto);

    CategoryEntity getById(Long id);
}
