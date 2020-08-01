package com.example.productsale.service;

import com.example.productsale.dao.model.CustomerEntity;
import com.example.productsale.dao.repository.CustomerRepository;
import com.example.productsale.dto.CustomerDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CustomerServiceImpl extends AbstractBaseService<CustomerEntity, CustomerDTO> implements CustomerService {

    private ModelMapper modelMapper = null;

    @Autowired
    UserService userService;

    @Autowired
    CustomerRepository repository;

    @Override
    protected void specificMapToDTO(CustomerEntity entity, CustomerDTO dto) {
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
    protected Class<CustomerEntity> getEntityClass() {
        return CustomerEntity.class;
    }

    @Override
    protected Class<CustomerDTO> getDtoClass() {
        return CustomerDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public CustomerDTO create(CustomerDTO dto) {
        CustomerEntity entity = mapToEntity(dto);
        CustomerEntity checkName = repository.findFirstByNameIgnoreCase(dto.getName().trim());
        if (checkName != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public CustomerDTO update(Long id, CustomerDTO dto) {
        CustomerEntity entity = repository.getById(id);
        if (!entity.getName().equals(dto.getName().trim()) && repository.findFirstByNameIgnoreCase(dto.getName().trim()) != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        entity.setPhone(dto.getPhone());
        entity.setFax(dto.getFax());
        dto.setUpdatedName(userService.getUsernameById(getUserIdFromAuthorization()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public CustomerDTO delete(Long id) {
        CustomerEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }

    @Override
    public CustomerDTO search(CustomerDTO dto) {
        CustomerEntity entity = repository.search(dto);
        return mapToDTO(entity);
    }
}
