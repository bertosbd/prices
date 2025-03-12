package com.company.prices.domain.port;

import com.company.prices.domain.entity.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PricesPersistencePort {

    Optional<Price> getPriceByProductBrandAndDateWithMaxPriority(Integer idProduct, Integer idBrand, LocalDateTime date);

}
