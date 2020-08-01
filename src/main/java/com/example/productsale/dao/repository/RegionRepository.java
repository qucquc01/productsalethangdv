package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

    RegionEntity getById(Long id);

}
