package com.example.productsale.dao.repository;

import com.example.productsale.dao.model.MenuEntity;
import com.example.productsale.dto.MenuDTO;
import com.example.productsale.enums.ActiveEnum;
import com.example.productsale.enums.LevelEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    MenuEntity getById(Long id);

    MenuEntity findFirstByNameIgnoreCase(String name);

    Boolean existsByLocationAndLevelAndParentId(Integer location, LevelEnum level, Long parentId);

    MenuEntity findByLocationAndLevelAndName(Integer location, LevelEnum level, String name);

    Boolean existsByParentId(Long parentId);

    @Query(value = "select n from MenuEntity n" +
            " where 1 = 1 " +
            " and ( n.parentId  = :parentId or :parentId is null)" +
            " and ( n.id = :id or :id is null)" +
            " and ( n.location = :location or :location is null)" +
            " and ( n.level = :level or :level is null)" +
            " and ( n.active = :active)")
    List<MenuEntity> findMenuByParentId(Long parentId, Long id, Integer location, LevelEnum level, Boolean active);

    @Query(value = " select n from MenuEntity n" +
            " where 1 = 1" +
            " and (n.parentId = :parentId)" +
            " and (n.level = :level)" +
            " and (n.location = :location)" +
            " and (n.active = true )")
    MenuEntity findByByParentId(Long parentId, LevelEnum level, Integer location);

    @Query(value = "select distinct n from MenuEntity n" +
            " left join MenuEntity mp on mp.parentId = n.id" +
            " where 1 = 1 " +
            " and ( lower(n.name) like lower(concat('%', cast(:#{#dto.name} as string ), '%')) or :#{#dto.name} is null)" +
            " and ( n.level = :#{#dto.level} or :#{#dto.level} is null)" +
            " and ( n.parentId = :#{#dto.parentId} or :#{#dto.parentId} is null)" +
            " and ( lower(n.link) like lower(concat('%', cast(:#{#dto.link} as string), '%')) or :#{#dto.link} is null)" +
            " and ( n.location = :#{#dto.location} or :#{#dto.location} is null)" +
            " and ( n.active = :#{#dto.active} or :#{#dto.active} is null)" +
            " and ( lower(n.name) like lower(concat('%',cast(:#{#dto.name} as string ), '%')) or :#{#dto.name} is null)" +
            " and ( lower(mp.name) like lower(concat('%',cast(:#{#dto.subMenu} as string ), '%')) or :#{#dto.subMenu} is null)")
    MenuEntity search(MenuDTO dto);
}
