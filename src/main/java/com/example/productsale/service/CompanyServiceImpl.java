package com.example.productsale.service;

import com.example.productsale.dao.model.CompanyEntity;
import com.example.productsale.dao.repository.CompanyRepository;
import com.example.productsale.dto.CompanyDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CompanyServiceImpl extends AbstractBaseService<CompanyEntity, CompanyDTO> implements CompanyService {

    private ModelMapper modelMapper = null;

    @Autowired
    UserService userService;

    @Autowired
    CompanyRepository repository;

    @Override
    protected ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STRICT);
        }
        return modelMapper;
    }

    @Override
    protected Class<CompanyEntity> getEntityClass() {
        return CompanyEntity.class;
    }

    @Override
    protected Class<CompanyDTO> getDtoClass() {
        return CompanyDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public CompanyDTO create(CompanyDTO dto) {
        CompanyEntity entity = mapToEntity(dto);
        CompanyEntity checkName = repository.findFirstByNameIgnoreCase(dto.getName().trim());
        if (checkName != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public CompanyDTO update(Long id, CompanyDTO dto) {
        CompanyEntity entity = repository.getById(id);
        if (!entity.getName().equals(dto.getName().trim()) && repository.findFirstByNameIgnoreCase(dto.getName().trim()) != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setCity(dto.getCity());
        entity.setPhone(dto.getPhone());
        entity.setFax(dto.getFax());
        dto.setUpdatedName(userService.getUsernameById(getUserIdFromAuthorization()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public CompanyDTO delete(Long id) {
        CompanyEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }

    @Override
    public CompanyDTO search(CompanyDTO dto) {
        CompanyEntity entity = repository.search(dto);
        return mapToDTO(entity);
    }
}
