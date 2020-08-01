package com.example.productsale.service;

import com.example.productsale.dto.ShipperDTO;

public interface ShipperService {

    ShipperDTO create(ShipperDTO dto);

    ShipperDTO update(Long id, ShipperDTO dto);

    ShipperDTO delete(Long id);

    ShipperDTO search(ShipperDTO dto);
}
