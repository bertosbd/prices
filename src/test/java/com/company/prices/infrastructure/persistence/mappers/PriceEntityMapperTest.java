package com.company.prices.infrastructure.persistence.mappers;

import com.company.prices.domain.entity.Price;
import com.company.prices.infrastructure.persistence.model.PriceEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceEntityMapperTest {

    private final PriceEntityMapper priceEntityMapper = Mappers.getMapper(PriceEntityMapper.class);

    @Test
    void givenEntityPrice_ShouldReturnPrice() {
        PriceEntity priceEntity = getPriceEntity(1);

        Price price = priceEntityMapper.toPrice(priceEntity);

        assertAll("PriceEntity is mapped to Price",
                () -> assertEquals(price.getProductId(), priceEntity.getProductId()),
                () -> assertEquals(price.getBrandId(), priceEntity.getBrandId()),
                () -> assertEquals(price.getPriceList(), priceEntity.getPriceList()),
                () -> assertEquals(price.getStartDate(), priceEntity.getStartDate()),
                () -> assertEquals(price.getEndDate(), priceEntity.getEndDate()),
                () -> assertEquals(price.getPrice(), priceEntity.getPrice())
        );
    }

    @Test
    void givenNullEntityPrices_ShouldReturnNullPrice() {
        Price priceResponse = priceEntityMapper.toPrice(null);
        assertNull(priceResponse);
    }

    private static PriceEntity getPriceEntity(int id) {
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setProductId(id);
        priceEntity.setBrandId(1);
        priceEntity.setPriceList(1);
        priceEntity.setStartDate(LocalDateTime.of(2020, 6, 14, 10, 0));
        priceEntity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        priceEntity.setPrice(BigDecimal.valueOf(100.50));
        return priceEntity;
    }
}