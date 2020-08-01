package com.example.productsale.service;


import com.example.productsale.dto.LoginDTO;
import com.example.productsale.dto.UserDTO;

import java.util.Map;

public interface UserService {
    UserDTO create(UserDTO dto);

    UserDTO update(Long id, UserDTO dto);

    UserDTO delete(Long id);

    UserDTO search(UserDTO dto);

    Map<String, Object> login(LoginDTO loginDTO);

    void init();

    String getUsernameById(Long id);
}
