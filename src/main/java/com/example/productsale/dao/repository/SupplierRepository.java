package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.SupplierEntity;
import com.example.productsale.dto.SupplierDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
    SupplierEntity findFirstByNameIgnoreCase(String name);

    SupplierEntity getById(Long id);

    @Query(value = "select s from SupplierEntity s" +
            " where 1 = 1" +
            " and (s.name like :#{#dto.name} or :#{#dto.name} is null )" +
            " and (s.address like :#{#dto.address} or :#{#dto.address} is null )" +
            " and (s.region like :#{#dto.region} or :#{#dto.region} is null )" +
            " and (s.phone like :#{#dto.phone} or :#{#dto.phone} is null )" +
            " and (s.fax like :#{#dto.fax} or :#{#dto.fax} is null )")
    SupplierEntity search(SupplierDTO dto);
}
