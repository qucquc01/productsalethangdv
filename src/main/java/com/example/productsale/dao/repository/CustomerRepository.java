package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.CustomerEntity;
import com.example.productsale.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(value = "select c from CustomerEntity c" +
            " where 1 = 1" +
            " and (c.name like :#{#dto.name} or :#{#dto.name} is null )" +
            " and (c.address like :#{#dto.address} or :#{#dto.address} is null )" +
            " and (c.country like :#{#dto.country} or :#{#dto.country} is null )" +
            " and (c.city like :#{#dto.city} or :#{#dto.city} is null )" +
            " and (c.phone like :#{#dto.phone} or :#{#dto.phone} is null )" +
            " and (c.companyId = :#{#dto.companyId} or :#{#dto.companyId} is null )" +
            " and (c.fax like :#{#dto.fax} or :#{#dto.fax} is null )")
    CustomerEntity search(CustomerDTO dto);

    CustomerEntity findFirstByNameIgnoreCase(String name);

    CustomerEntity getById(Long id);
}
