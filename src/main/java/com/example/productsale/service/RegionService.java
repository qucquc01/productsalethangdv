package com.example.productsale.service;

import com.example.productsale.dto.RegionDTO;

public interface RegionService {

    RegionDTO create(RegionDTO dto);

    RegionDTO update(Long id, RegionDTO dto);

    RegionDTO delete(Long id);
}
