package com.example.productsale.controller;

import com.example.productsale.dto.LoginDTO;
import com.example.productsale.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController extends BaseController {

    @Autowired
    UserService service;

    @PostMapping("/login")
    @Transactional(readOnly = true)
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO){
        return response(service.login(loginDTO));
    }

    @Override
    protected Logger getLogger() {
        return null;
    }
}
