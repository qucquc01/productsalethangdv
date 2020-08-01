package com.example.productsale.controller;

import com.example.productsale.dto.UserDTO;
import com.example.productsale.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
public class UserController extends BaseController {

    @Autowired
    UserService service;


    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDTO dto) {
        return response(service.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto){
        return response(service.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id){
        return response(service.delete(id));
    }

    @GetMapping("/search")
    public ResponseEntity<UserDTO> search(UserDTO dto){
        return response(service.search(dto));
    }

    @Override
    protected Logger getLogger() {
        return null;
    }
}
