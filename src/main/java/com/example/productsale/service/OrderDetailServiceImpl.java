package com.example.productsale.service;

import com.example.productsale.dao.model.OrderDetailEntity;
import com.example.productsale.dao.repository.OrderDetailRepository;
import com.example.productsale.dto.OrderDetailDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class OrderDetailServiceImpl extends AbstractBaseService<OrderDetailEntity, OrderDetailDTO> implements OrderDetailService {

    private ModelMapper modelMapper = null;

    @Autowired
    UserService userService;

    @Autowired
    OrderDetailRepository repository;

    @Override
    protected void specificMapToDTO(OrderDetailEntity entity, OrderDetailDTO dto) {
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
    protected Class<OrderDetailEntity> getEntityClass() {
        return OrderDetailEntity.class;
    }

    @Override
    protected Class<OrderDetailDTO> getDtoClass() {
        return OrderDetailDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public OrderDetailDTO create(OrderDetailDTO dto) {
        OrderDetailEntity entity = mapToEntity(dto);

        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public OrderDetailDTO update(Long id, OrderDetailDTO dto) {
        OrderDetailEntity entity = repository.getById(id);

        entity.setProductId(dto.getProductId());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setDiscount(dto.getDiscount());
        entity.setQuantity(dto.getQuantity());
        entity.setOrderId(dto.getOrderId());
        dto.setUpdatedName(userService.getUsernameById(getUserIdFromAuthorization()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public OrderDetailDTO delete(Long id) {
        OrderDetailEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }

    @Override
    public OrderDetailDTO search(OrderDetailDTO dto) {
        OrderDetailEntity entity = repository.search(dto);
        return mapToDTO(entity);
    }
}
