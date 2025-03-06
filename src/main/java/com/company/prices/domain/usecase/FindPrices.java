package com.company.prices.domain.usecase;

import com.company.prices.domain.entity.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FindPrices {

    Optional<Price> getProductPriceByBrandAndDateWithMaxPriority(Integer idProduct, Integer idBrand, LocalDateTime date);

}
