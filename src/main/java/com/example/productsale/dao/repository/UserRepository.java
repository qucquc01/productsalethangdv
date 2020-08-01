package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.UserEntity;
import com.example.productsale.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getById(Long id);

    @Query(value = "select u from UserEntity u" +
        " where 1 = 1" +
        " and (u.name like :#{#userDTO.name} or :#{#userDTO.name} is null )" +
        " and (u.username like :#{#userDTO.username} or :#{#userDTO.username} is null )" +
        " and (u.email like :#{#userDTO.email} or :#{#userDTO.email} is null )" +
        " and (u.gender like :#{#userDTO.gender} or :#{#userDTO.gender} is null )")
    UserEntity search(UserDTO userDTO);

    UserEntity findFirstByUsername(String username);

    UserEntity findByUsername(String username);

    @Transactional(readOnly = true)
    @Query("select e.username from UserEntity e where e.id = ?1")
    String getUsernameById(Long id);
}
