package com.company.prices.infrastructure.persistence.adapter;

import com.company.prices.domain.entity.Price;
import com.company.prices.infrastructure.persistence.jpa.PricesRepository;
import com.company.prices.infrastructure.persistence.mappers.PriceEntityMapper;
import com.company.prices.infrastructure.persistence.model.PriceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricesPersistenceAdapterTest {

    @Mock
    PricesRepository pricesRepository;

    @Spy
    PriceEntityMapper priceEntityMapper = Mappers.getMapper(PriceEntityMapper.class);

    @InjectMocks
    PricesPersistenceAdapter pricesPersistenceAdapter;

    @Test
    void givenPriceCriteria_whenPriceFound_thenPriceIsReturned() {
        PriceEntity priceEntity = new PriceEntity(1, 1, LocalDateTime.now(), LocalDateTime.now(), 1, 1,
                2, BigDecimal.ONE, "EUR");

        when(pricesRepository.getPriceByProductBrandAndDateWithMaxPriority(any(), any(), any()))
                .thenReturn(Optional.of(priceEntity));

        Optional<Price> result = pricesPersistenceAdapter.
                getPriceByProductBrandAndDateWithMaxPriority(1, 1, LocalDateTime.now());

        assertAll("Search price by product, brand and date and get max priority price",
                () -> assertTrue(result.isPresent(), "Price found"),
                () -> assertEquals(priceEntityMapper.toPrice(priceEntity), result.get(), "Expected price is found"));
    }

    @Test
    void givenPriceCriteria_whenPriceNotFound_thenReturnEmptyPrice() {

        when(pricesRepository.getPriceByProductBrandAndDateWithMaxPriority(any(), any(), any())).thenReturn(Optional.empty());

        Optional<Price> result = pricesPersistenceAdapter.
                getPriceByProductBrandAndDateWithMaxPriority(1, 1, LocalDateTime.now());

        assertTrue(result.isEmpty(), "Price not found found");
    }
}