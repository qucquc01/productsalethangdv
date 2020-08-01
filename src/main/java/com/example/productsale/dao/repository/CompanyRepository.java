package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.CompanyEntity;
import com.example.productsale.dto.CompanyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    CompanyEntity getById(Long id);

    @Query(value = "select c from CompanyEntity c" +
            " where 1 = 1" +
            " and (c.name like :#{#dto.name} or :#{#dto.name} is null )" +
            " and (c.address like :#{#dto.address} or :#{#dto.address} is null )" +
            " and (c.country like :#{#dto.country} or :#{#dto.country} is null )" +
            " and (c.city like :#{#dto.city} or :#{#dto.city} is null )" +
            " and (c.phone like :#{#dto.phone} or :#{#dto.phone} is null )" +
            " and (c.fax like :#{#dto.fax} or :#{#dto.fax} is null )")
    CompanyEntity search(CompanyDTO dto);

    CompanyEntity findFirstByNameIgnoreCase(String name);
}
