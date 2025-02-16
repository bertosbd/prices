package com.company.prices.application.port;

import com.company.prices.domain.entity.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PricesPersistencePort {

    List<Price> getPricesByProductBrandAndDate(Integer idProduct, Integer idBrand, LocalDateTime date);

}
