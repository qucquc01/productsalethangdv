package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.EmployeeEntity;
import com.example.productsale.dto.EmployeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    EmployeeEntity getById(Long id);

    @Query(value = "select e from EmployeeEntity e" +
            " where 1 = 1" +
            " and (e.name like :#{#dto.name} or :#{#dto.name} is null )" +
            " and (e.country like :#{#dto.country} or :#{#dto.country} is null )" +
            " and (e.employeeRegionId = :#{#dto.employeeRegionId} or :#{#dto.employeeRegionId} is null )" +
            " and (e.city like :#{#dto.city} or :#{#dto.city} is null )")
    EmployeeEntity search(EmployeeDTO dto);

    EmployeeEntity findFirstByNameIgnoreCase(String name);
}
