package com.example.productsale.service;

import com.example.productsale.dao.model.EmployeeRegionEntity;
import com.example.productsale.dao.repository.EmployeeRegionRepository;
import com.example.productsale.dto.EmployeeRegionDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmployeeRegionServiceImpl extends AbstractBaseService<EmployeeRegionEntity, EmployeeRegionDTO> implements EmployeeRegionService {

    private ModelMapper modelMapper = null;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeRegionRepository repository;

    @Override
    protected void specificMapToDTO(EmployeeRegionEntity entity, EmployeeRegionDTO dto) {
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
    protected Class<EmployeeRegionEntity> getEntityClass() {
        return EmployeeRegionEntity.class;
    }

    @Override
    protected Class<EmployeeRegionDTO> getDtoClass() {
        return EmployeeRegionDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public EmployeeRegionDTO create(EmployeeRegionDTO dto) {
        EmployeeRegionEntity entity = mapToEntity(dto);

        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public EmployeeRegionDTO update(Long id, EmployeeRegionDTO dto) {
        EmployeeRegionEntity entity = repository.getById(id);

        entity.setRegionId(dto.getRegionId());
        dto.setUpdatedName(userService.getUsernameById(getUserIdFromAuthorization()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public EmployeeRegionDTO delete(Long id) {
        EmployeeRegionEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }

    @Override
    public EmployeeRegionDTO search(EmployeeRegionDTO dto) {
        EmployeeRegionEntity entity = repository.search(dto);
        return mapToDTO(entity);
    }
}
