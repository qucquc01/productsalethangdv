package com.example.productsale.service;

import com.example.productsale.dao.model.MenuEntity;
import com.example.productsale.dao.repository.MenuRepository;
import com.example.productsale.dto.LevelDTO;
import com.example.productsale.dto.MenuDTO;
import com.example.productsale.enums.ActiveEnum;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.apache.commons.lang3.ArrayUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class MenuServiceImpl extends AbstractBaseService<MenuEntity, MenuDTO> implements MenuService {

    private ModelMapper modelMapper = null;

    @Autowired
    private MenuRepository repository;

    @Override
    protected ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STRICT);
        }
        return modelMapper;
    }

    @Override
    protected Class<MenuEntity> getEntityClass() {
        return MenuEntity.class;
    }

    @Override
    protected Class<MenuDTO> getDtoClass() {
        return MenuDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public MenuDTO create(MenuDTO dto) {
        MenuEntity entity = mapToEntity(dto);
        if (entity.getActive() == false){
            if (repository.existsByLocationAndLevelAndParentId(dto.getLocation(), dto.getLevel(), dto.getParentId()) && dto.getActive() == true){
                throw new BaseException(1, Msg.getMessage("Ton tai vi tri trong he thong"));
            }
        }

        MenuEntity checkName = repository.findByLocationAndLevelAndName(dto.getLocation(), dto.getLevel(), dto.getName());
        if (checkName != null && entity.getActive() == true){
            throw new BaseException(2, Msg.getMessage("Ton tai ten trong he thong"));
        }

        if (dto.getParentId() == null){
            entity.setParentId(null);
        }
        entity.setParentId(dto.getParentId());

        if (entity.getLocation() == 0){
            entity.setActive(false);
        }

        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public MenuDTO update(Long id, MenuDTO dto) {
        MenuEntity entity = repository.getById(id);
        if (repository.existsByParentId(dto.getParentId())){
            entity.setParentId(dto.getParentId());
        }
        if (!entity.getName().equals(dto.getName()) && repository.findFirstByNameIgnoreCase(dto.getName().trim()) != null){
            throw new BaseException(2, Msg.getMessage("Ton tai ten trong he thong"));
        }
        if (dto.getActive() == true){
            if (!entity.getLocation().equals(dto.getLocation()) || !entity.getLevel().equals(dto.getLevel()) || !entity.getParentId().equals(dto.getParentId())){
                if (repository.findMenuByParentId(dto.getParentId(), entity.getId(), dto.getLocation(), dto.getLevel(), true) != null){
                    throw new BaseException(2, Msg.getMessage("Ton tai ten trong he thong"));
                }
            }
        }
        entity.setParentId(dto.getParentId());

        if (dto.getLocation() == 0) {
            entity.setLocation(0);
            entity.setActive(false);
        }
        if (dto.getActive() == false && dto.getLocation() == 0) {
            throw new BaseException(3, Msg.getMessage("Thay doi vi tri"));
        }
        entity.setLocation(dto.getLocation());
        entity.setActive(dto.getActive());
        entity.setName(dto.getName());
        entity.setLink(dto.getLink());
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public MenuDTO delete(Long id) {
        MenuEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }

    @Override
    public MenuDTO search(MenuDTO dto) {
        MenuEntity entity = repository.search(dto);
        return mapToDTO(entity);
    }

    @Override
    public MenuDTO active(Long id) {
        MenuEntity entity = repository.getById(id);
        if (entity == null) {
            throw new BaseException(1, Msg.getMessage("Khong ton tai"));
        }

        if (entity.getActive() == false && entity.getLocation() == 0) {
            throw new BaseException(3, Msg.getMessage("Thay doi vi tri"));
        }

        if (entity.getActive() == false) {
            MenuEntity check = repository.findByByParentId(entity.getParentId(), entity.getLevel(), entity.getLocation());
            if (check != null) {
                throw new BaseException(2, Msg.getMessage("Ton tai menu con"));
            }
        }

        if (entity.getActive() == false) {
            List<MenuEntity> menus = entity.getChildren();
            for (MenuEntity menu : menus) {
                menu.setActive(false);
                menu.setLocation(0);
                List<MenuEntity> menus1 = menu.getChildren();
                for (MenuEntity menu1 : menus1) {
                    menu1.setActive(false);
                    menu1.setLocation(0);
                }

            }
        }

        entity.setActive(!entity.getActive());

        if (entity.getLocation() != 0 || entity.getLocation() != 1) {
            if (entity.getActive() == false) {
                entity.setLocation(0);
            }
        }
        repository.save(entity);
        return null;
    }

    @Override
    public List<Map<String, Object>> getSubMenu(String name) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getHeaderMenu(LevelDTO dto) {
        return null;
    }
}
