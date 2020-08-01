package com.example.productsale.service;

import com.example.productsale.dao.model.OrderEntity;
import com.example.productsale.dao.repository.OrderRepository;
import com.example.productsale.dto.OrderDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class OrderServiceImpl extends AbstractBaseService<OrderEntity, OrderDTO> implements OrderService {

    private ModelMapper modelMapper = null;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository repository;

    @Override
    protected void specificMapToDTO(OrderEntity entity, OrderDTO dto) {
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
    protected Class<OrderEntity> getEntityClass() {
        return OrderEntity.class;
    }

    @Override
    protected Class<OrderDTO> getDtoClass() {
        return OrderDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public OrderDTO create(OrderDTO dto) {
        OrderEntity entity = mapToEntity(dto);

        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public OrderDTO update(Long id, OrderDTO dto) {
        OrderEntity entity = repository.getById(id);

        entity.setCustomerId(dto.getCustomerId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setShipperId(dto.getShipperId());
        dto.setUpdatedName(userService.getUsernameById(getUserIdFromAuthorization()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public OrderDTO delete(Long id) {
        OrderEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }

    @Override
    public OrderDTO search(OrderDTO dto) {
        OrderEntity entity = repository.search(dto);
        return mapToDTO(entity);
    }
}
