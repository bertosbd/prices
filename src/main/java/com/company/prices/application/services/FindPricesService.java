package com.company.prices.application.services;

import com.company.prices.domain.entity.Price;
import com.company.prices.domain.port.PricesPersistencePort;
import com.company.prices.domain.usecase.FindPrices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FindPricesService implements FindPrices {

    @Autowired
    private final PricesPersistencePort pricesPersistencePort;

    public Optional<Price> getProductPriceByBrandAndDateWithMaxPriority(Integer idProduct, Integer idBrand, LocalDateTime date) {

        return pricesPersistencePort.getPriceByProductBrandAndDateWithMaxPriority(idProduct, idBrand, date);

    }
}
