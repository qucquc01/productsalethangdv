package com.example.productsale.controller;

import com.example.productsale.dto.MenuDTO;
import com.example.productsale.dto.UserDTO;
import com.example.productsale.service.MenuService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody MenuDTO dto) {
        return response(service.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MenuDTO dto){
        return response(service.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return response(service.delete(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(MenuDTO dto){
        return response(service.search(dto));
    }

    @Override
    protected Logger getLogger() {
        return null;
    }
}
