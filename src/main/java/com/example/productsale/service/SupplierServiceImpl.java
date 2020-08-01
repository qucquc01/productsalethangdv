package com.example.productsale.service;

import com.example.productsale.dao.model.MenuEntity;
import com.example.productsale.dao.model.SupplierEntity;
import com.example.productsale.dao.repository.SupplierRepository;
import com.example.productsale.dto.SupplierDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class SupplierServiceImpl extends AbstractBaseService<SupplierEntity, SupplierDTO> implements SupplierService {

    private ModelMapper modelMapper = null;

    @Autowired
    SupplierRepository repository;

    @Autowired
    UserService userService;

    @Override
    protected void specificMapToDTO(SupplierEntity entity, SupplierDTO dto) {
        super.specificMapToDTO(entity, dto);
        dto.setCreatedName(userService.getUsernameById(getUserIdFromAuthorization()));
    }

    @Override
    protected ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STRICT);
        }
        return modelMapper;
    }

    @Override
    protected Class<SupplierEntity> getEntityClass() {
        return SupplierEntity.class;
    }

    @Override
    protected Class<SupplierDTO> getDtoClass() {
        return SupplierDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public SupplierDTO create(SupplierDTO dto) {
        SupplierEntity entity = mapToEntity(dto);
        SupplierEntity checkName = repository.findFirstByNameIgnoreCase(dto.getName().trim());
        if (checkName != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public SupplierDTO update(Long id, SupplierDTO dto) {
        SupplierEntity entity = repository.getById(id);
        if (!entity.getName().equals(dto.getName().trim()) && repository.findFirstByNameIgnoreCase(dto.getName().trim()) != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setRegion(dto.getRegion());
        entity.setPhone(dto.getPhone());
        entity.setFax(dto.getFax());
        dto.setUpdatedName(userService.getUsernameById(getUserIdFromAuthorization()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public SupplierDTO delete(Long id) {
        SupplierEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }

    @Override
    public SupplierDTO search(SupplierDTO dto) {
        SupplierEntity entity = repository.search(dto);
        return mapToDTO(entity);
    }
}
