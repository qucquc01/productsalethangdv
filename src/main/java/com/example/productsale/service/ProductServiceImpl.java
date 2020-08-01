package com.example.productsale.service;

import com.example.productsale.dao.model.ProductEntity;
import com.example.productsale.dao.repository.ProductRepository;
import com.example.productsale.dto.ProductDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ProductServiceImpl extends AbstractBaseService<ProductEntity, ProductDTO> implements ProductService {

    private ModelMapper modelMapper = null;

    @Autowired
    UserService userService;

    @Autowired
    ProductRepository repository;

    @Override
    protected void specificMapToDTO(ProductEntity entity, ProductDTO dto) {
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
    protected Class<ProductEntity> getEntityClass() {
        return ProductEntity.class;
    }

    @Override
    protected Class<ProductDTO> getDtoClass() {
        return ProductDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public ProductDTO create(ProductDTO dto) {
        ProductEntity entity = mapToEntity(dto);
        ProductEntity checkName = repository.findFirstByNameIgnoreCase(dto.getName().trim());
        if (checkName != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        ProductEntity entity = repository.getById(id);
        if (!entity.getName().equals(dto.getName().trim()) && repository.findFirstByNameIgnoreCase(dto.getName().trim()) != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        entity.setName(dto.getName());
        entity.setCategoryId(dto.getCategoryId());
        entity.setSupplierId(dto.getSupplierId());
        entity.setPrice(dto.getPrice());
        entity.setDiscount(dto.getDiscount());
        dto.setUpdatedName(userService.getUsernameById(getUserIdFromAuthorization()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public ProductDTO delete(Long id) {
        ProductEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }

    @Override
    public ProductDTO search(ProductDTO dto) {
        ProductEntity entity = repository.search(dto);
        return mapToDTO(entity);
    }
}
