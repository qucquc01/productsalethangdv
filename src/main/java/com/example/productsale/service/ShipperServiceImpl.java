package com.example.productsale.service;

import com.example.productsale.dao.model.ShipperEntity;
import com.example.productsale.dao.repository.ShipperRepository;
import com.example.productsale.dto.ShipperDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ShipperServiceImpl extends AbstractBaseService<ShipperEntity, ShipperDTO> implements ShipperService {

    private ModelMapper modelMapper = null;

    @Autowired
    UserService userService;

    @Autowired
    ShipperRepository repository;

    @Override
    protected void specificMapToDTO(ShipperEntity entity, ShipperDTO dto) {
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
    protected Class<ShipperEntity> getEntityClass() {
        return ShipperEntity.class;
    }

    @Override
    protected Class<ShipperDTO> getDtoClass() {
        return ShipperDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public ShipperDTO create(ShipperDTO dto) {
        ShipperEntity entity = mapToEntity(dto);
        ShipperEntity checkName = repository.findFirstByNameIgnoreCase(dto.getName().trim());
        if (checkName != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public ShipperDTO update(Long id, ShipperDTO dto) {
        ShipperEntity entity = repository.getById(id);
        if (!entity.getName().equals(dto.getName().trim()) && repository.findFirstByNameIgnoreCase(dto.getName().trim()) != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        entity.setPostCode(dto.getPostCode());
        entity.setCompanyId(dto.getCompanyId());
        entity.setPhone(dto.getPhone());
        entity.setFax(dto.getFax());
        dto.setUpdatedName(userService.getUsernameById(getUserIdFromAuthorization()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public ShipperDTO delete(Long id) {
        ShipperEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }

    @Override
    public ShipperDTO search(ShipperDTO dto) {
        ShipperEntity entity = repository.search(dto);
        return mapToDTO(entity);
    }
}
