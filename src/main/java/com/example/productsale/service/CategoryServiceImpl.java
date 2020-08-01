package com.example.productsale.service;

import com.example.productsale.dao.model.CategoryEntity;
import com.example.productsale.dao.repository.CategoryRepository;
import com.example.productsale.dto.CategoryDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CategoryServiceImpl extends AbstractBaseService<CategoryEntity, CategoryDTO> implements CategoryService {

    private ModelMapper modelMapper = null;

    @Autowired
    private CategoryRepository repository;

    @Autowired
    UserService userService;

    @Override
    protected ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STRICT);
        }
        return modelMapper;
    }

    @Override
    protected Class<CategoryEntity> getEntityClass() {
        return CategoryEntity.class;
    }

    @Override
    protected Class<CategoryDTO> getDtoClass() {
        return CategoryDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return null;
    }

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity entity = mapToEntity(dto);
        if (repository.findFirstByNameIgnoreCase(entity.getName().trim()) != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {
        CategoryEntity entity = repository.getById(id);
        if (!entity.getName().equals(dto.getName()) && repository.findFirstByNameIgnoreCase(dto.getName().trim()) != null){
            throw new BaseException(1, Msg.getMessage("Tồn tại tên trong hệ thống"));
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setLink(dto.getName());
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public CategoryDTO delete(Long id) {
        CategoryEntity entity = repository.getById(id);
        repository.delete(entity);
        return mapToDTO(entity);
    }

    @Override
    public CategoryDTO search(CategoryDTO dto) {
        return mapToDTO(repository.search(dto));
    }
}
