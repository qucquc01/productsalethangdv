package com.example.productsale.service;

import com.example.productsale.dao.model.RegionEntity;
import com.example.productsale.dao.repository.RegionRepository;
import com.example.productsale.dto.RegionDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RegionServiceImpl extends AbstractBaseService<RegionEntity, RegionDTO> implements RegionService {

    private ModelMapper modelMapper = null;

    @Autowired
    RegionRepository repository;

    @Autowired
    UserService userService;

    @Override
    protected void specificMapToDTO(RegionEntity entity, RegionDTO dto) {
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
    protected Class<RegionEntity> getEntityClass() {
        return RegionEntity.class;
    }

    @Override
    protected Class<RegionDTO> getDtoClass() {
        return RegionDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public RegionDTO create(RegionDTO dto) {
        RegionEntity entity = mapToEntity(dto);

        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public RegionDTO update(Long id, RegionDTO dto) {
        RegionEntity entity = repository.getById(id);

        entity.setDescription(dto.getDescription());
        dto.setUpdatedName(userService.getUsernameById(getUserIdFromAuthorization()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public RegionDTO delete(Long id) {
        RegionEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }
}
