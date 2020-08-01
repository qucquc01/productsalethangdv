package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.ShipperEntity;
import com.example.productsale.dto.ShipperDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends JpaRepository<ShipperEntity, Long> {

    @Query(value = "select s from ShipperEntity s" +
            " where 1 = 1" +
            " and (s.name like :#{#dto.name} or :#{#dto.name} is null )" +
            " and (s.address like :#{#dto.address} or :#{#dto.address} is null )" +
            " and (s.country like :#{#dto.country} or :#{#dto.country} is null )" +
            " and (s.city like :#{#dto.city} or :#{#dto.city} is null )" +
            " and (s.phone like :#{#dto.phone} or :#{#dto.phone} is null )" +
            " and (s.companyId = :#{#dto.companyId} or :#{#dto.companyId} is null )" +
            " and (s.postCode like :#{#dto.postCode} or :#{#dto.postCode} is null )" +
            " and (s.phone = :#{#dto.phone} or :#{#dto.phone} is null )" +
            " and (s.companyId like :#{#dto.companyId} or :#{#dto.companyId} is null )")
    ShipperEntity search(ShipperDTO dto);

    ShipperEntity findFirstByNameIgnoreCase(String name);

    ShipperEntity getById(Long id);
}
