package com.example.productsale.controller;

import com.example.productsale.dto.CustomerDTO;
import com.example.productsale.service.CustomerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/customer")
public class CustomerController extends BaseController {

    @Autowired
    CustomerService service;

    @Override
    protected Logger getLogger() {
        return null;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CustomerDTO dto) {
        return response(service.create(dto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CustomerDTO dto){
        return response(service.update(id, dto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return response(service.delete(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(CustomerDTO dto){
        return response(service.search(dto));
    }
}
