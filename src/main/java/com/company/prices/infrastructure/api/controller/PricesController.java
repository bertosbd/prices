package com.company.prices.infrastructure.api.controller;

import com.company.prices.domain.entity.Price;
import com.company.prices.domain.usecase.FindPrices;
import com.company.prices.infrastructure.api.dto.PriceResponse;
import com.company.prices.infrastructure.api.exception.PriceNotFoundException;
import com.company.prices.infrastructure.api.mappers.PricesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class PricesController implements PricesApi {

    private final FindPrices findPrices;

    private final PricesMapper pricesMapper;

    @Autowired
    public PricesController(FindPrices findPrices, PricesMapper pricesMapper) {
        this.findPrices = findPrices;
        this.pricesMapper = pricesMapper;
    }

    @Override
    public ResponseEntity<PriceResponse> getProductPriceByBrandAndDate(Integer productId, Integer brandId,
                                                                       LocalDateTime priceDate) throws PriceNotFoundException {

        Optional<Price> price = findPrices.getProductPriceByBrandAndDateWithMaxPriority(productId, brandId, priceDate);

        return price.map(p -> ResponseEntity.status(HttpStatus.OK).body(pricesMapper.toPriceResponse(p)))
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, priceDate));

    }

}
