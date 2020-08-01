package com.example.productsale.service;

import com.example.productsale.dao.model.BaseEntity;
import com.example.productsale.dao.model.UserEntity;
import com.example.productsale.dto.BaseDTO;
import com.example.productsale.dto.UserPrincipal;
import com.example.productsale.security.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public abstract class AbstractBaseService<X extends BaseEntity, Y extends BaseDTO> {

    protected abstract ModelMapper getModelMapper();

    protected abstract Class<X> getEntityClass();

    protected abstract Class<Y> getDtoClass();

    protected abstract Logger getLogger();

    final protected X mapToEntity(Y dto) {
        if (dto == null) {
            return null;
        }

        X entity = getModelMapper().map(dto, getEntityClass());


        specificMapToEntity(dto, entity);

        return entity;
    }

    final protected void mapToEntity(Y dto, X entity) {
        if (dto == null) {
            return;
        }

        getModelMapper().map(dto, entity);


        specificMapToEntity(dto, entity);
    }

    final protected Y mapToDTO(X entity) {
        if (entity == null) {
            return null;
        }

        Y dto = getModelMapper().map(entity, getDtoClass());

        specificMapToDTO(entity, dto);

        return dto;
    }

    final protected void mapToDTO(X entity, Y dto) {
        if (entity == null) {
            return;
        }

        getModelMapper().map(entity, dto);

        specificMapToDTO(entity, dto);
    }

    protected void specificMapToDTO(X entity, Y dto) {

    }

    protected void specificMapToEntity(Y dto, X entity) {

    }

    protected UserPrincipal getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken){
                return null;
            };
            return (UserPrincipal) authentication.getPrincipal();
    }

    protected String getUsername(){
        UserPrincipal user = getUser();
        return user.getUsername();
    }
    protected Long getUserId() {
        UserPrincipal userPrincipal = getUser();

        return userPrincipal == null ? null : userPrincipal.getId();
    }

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    protected Long getUserIdFromAuthorization(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        return userId;
    }
}
