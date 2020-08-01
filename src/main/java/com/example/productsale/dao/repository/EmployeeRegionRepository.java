package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.EmployeeRegionEntity;
import com.example.productsale.dto.EmployeeRegionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRegionRepository extends JpaRepository<EmployeeRegionEntity, Long> {

    EmployeeRegionEntity getById(Long id);

    @Query(value = "select e from EmployeeRegionEntity e" +
            " where 1 = 1" +
            " and (e.regionId = :#{#dto.regionId} or :#{#dto.regionId} is null )")
    EmployeeRegionEntity search(EmployeeRegionDTO dto);


}
