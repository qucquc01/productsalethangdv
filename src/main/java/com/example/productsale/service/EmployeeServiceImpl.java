package com.example.productsale.service;

import com.example.productsale.dao.model.EmployeeEntity;
import com.example.productsale.dao.repository.EmployeeRepository;
import com.example.productsale.dto.EmployeeDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmployeeServiceImpl extends AbstractBaseService<EmployeeEntity, EmployeeDTO> implements EmployeeService {

    private ModelMapper modelMapper = null;

    @Autowired
    EmployeeRepository repository;

    @Autowired
    UserService userService;

    @Override
    protected void specificMapToDTO(EmployeeEntity entity, EmployeeDTO dto) {
        super.specificMapToDTO(entity, dto);
        dto.setCreatedName(userService.getUsernameById(getUserIdFromAuthorization()));
        dto.setUpdatedName(userService.getUsernameById(getUserIdFromAuthorization()));
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
    protected Class<EmployeeEntity> getEntityClass() {
        return EmployeeEntity.class;
    }

    @Override
    protected Class<EmployeeDTO> getDtoClass() {
        return EmployeeDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public EmployeeDTO create(EmployeeDTO dto) {
        EmployeeEntity entity = mapToEntity(dto);
        EmployeeEntity checkName = repository.findFirstByNameIgnoreCase(dto.getName().trim());
        if (checkName != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO dto) {
        EmployeeEntity entity = repository.getById(id);
        if (!entity.getName().equals(dto.getName().trim()) && repository.findFirstByNameIgnoreCase(dto.getName().trim()) != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        entity.setName(dto.getName());
        entity.setCountry(dto.getCountry());
        entity.setCity(dto.getCity());
        entity.setLink(dto.getLink());
        entity.setEmployeeRegionId(dto.getEmployeeRegionId());
        dto.setUpdatedName(userService.getUsernameById(getUserIdFromAuthorization()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public EmployeeDTO delete(Long id) {
        EmployeeEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }

    @Override
    public EmployeeDTO search(EmployeeDTO dto) {
        EmployeeEntity entity = repository.search(dto);
        return mapToDTO(entity);
    }
}
