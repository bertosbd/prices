package com.company.prices.infrastructure.api.exception;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(Integer productId, Integer brandId, LocalDateTime date) {
        super(String.format("Price not found for product %s, brand %s and date %s", productId, brandId, date));
    }

}
