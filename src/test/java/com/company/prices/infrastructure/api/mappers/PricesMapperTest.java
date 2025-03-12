package com.company.prices.infrastructure.api.mappers;

import com.company.prices.domain.entity.Price;
import com.company.prices.infrastructure.api.dto.PriceResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PricesMapperTest {

    private final PricesMapper pricesMapper = Mappers.getMapper(PricesMapper.class);

    @Test
    void givenPrice_ShouldReturnPriceResponse() {
        Price price = new Price();
        price.setProductId(1);
        price.setBrandId(1);
        price.setPriceList(1);
        price.setStartDate(LocalDateTime.of(2020, 6, 14, 10, 0));
        price.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        price.setPrice(BigDecimal.valueOf(100.50));

        PriceResponse priceResponse = pricesMapper.toPriceResponse(price);

        assertAll("Price is mapped to PriceResponse",
                () -> assertEquals(String.valueOf(price.getProductId()), priceResponse.getIdProducto()),
                () -> assertEquals(String.valueOf(price.getBrandId()), priceResponse.getIdBrand()),
                () -> assertEquals(String.valueOf(price.getPriceList()), priceResponse.getPriceList()),
                () -> assertEquals(price.getStartDate(), priceResponse.getPriceStartDate()),
                () -> assertEquals(price.getEndDate(), priceResponse.getPriceEndDate()),
                () -> assertEquals(price.getPrice(), priceResponse.getPrice())
        );
    }

    @Test
    void givenNullPrices_ShouldReturnNullPriceResponse() {
        PriceResponse priceResponse = pricesMapper.toPriceResponse(null);
        assertNull(priceResponse);
    }
}