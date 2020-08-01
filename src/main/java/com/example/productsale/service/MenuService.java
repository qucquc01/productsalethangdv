package com.example.productsale.service;

import com.example.productsale.dto.LevelDTO;
import com.example.productsale.dto.MenuDTO;

import java.util.List;
import java.util.Map;

public interface MenuService {

    MenuDTO create(MenuDTO dto);

    MenuDTO update(Long id, MenuDTO dto);

    MenuDTO delete(Long id);

    MenuDTO search(MenuDTO dto);

    MenuDTO active(Long id);

    List<Map<String, Object>> getSubMenu(String name);

    List<Map<String, Object>> getHeaderMenu(LevelDTO dto);
}
