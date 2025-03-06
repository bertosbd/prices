package com.company.prices.infraestructure.persistence.adapter;

import com.company.prices.domain.entity.Price;
import com.company.prices.infraestructure.persistence.jpa.PricesRepository;
import com.company.prices.infraestructure.persistence.mappers.PriceEntityMapper;
import com.company.prices.infraestructure.persistence.model.PriceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

        when(pricesRepository.getPricesByProductBrandAndDate(any(), any(), any()))
                .thenReturn(List.of(priceEntity));

        List<Price> result = pricesPersistenceAdapter.
                getPricesByProductBrandAndDate(1, 1, LocalDateTime.now());

        assertAll("Search price by product, brand and date and get max priority price",
                () -> assertFalse(result.isEmpty(), "Price found"),
                () -> assertEquals(1, result.size(), "Price with max priority is the expected"),
                () -> assertEquals(priceEntityMapper.toPrice(priceEntity), result.get(0), "Expected price is found"));
    }

    @Test
    void givenPriceCriteria_whenPriceNotFound_thenEmptyListReturned() {

        when(pricesRepository.getPricesByProductBrandAndDate(any(), any(), any())).thenReturn(List.of());

        List<Price> result = pricesPersistenceAdapter.
                getPricesByProductBrandAndDate(1, 1, LocalDateTime.now());

        assertTrue(result.isEmpty(), "Price not found found");
    }

    @Test
    void givenPriceCriteria_whenMultiplePriceFound_thenAllPriceAreReturned() {
        PriceEntity priceEntity1 = new PriceEntity(1, 1, LocalDateTime.now(), LocalDateTime.now(), 1, 1,
                2, BigDecimal.ONE, "EUR");
        PriceEntity priceEntity2 = new PriceEntity(2, 1, LocalDateTime.now(), LocalDateTime.now(), 1, 1,
                2, BigDecimal.ONE, "EUR");
        PriceEntity priceEntity3 = new PriceEntity(3, 1, LocalDateTime.now(), LocalDateTime.now(), 1, 1,
                3, BigDecimal.ONE, "EUR");

        when(pricesRepository.getPricesByProductBrandAndDate(any(), any(), any()))
                .thenReturn(List.of(priceEntity1, priceEntity2, priceEntity3));

        List<Price> result = pricesPersistenceAdapter.
                getPricesByProductBrandAndDate(1, 1, LocalDateTime.now());

        assertAll("Search price by product, brand and datee",
                () -> assertFalse(result.isEmpty(), "Prices found"),
                () -> assertEquals(3, result.size(), "All expected prices are returned"),
                () -> assertTrue(result.contains(priceEntityMapper.toPrice(priceEntity1)), "Price 1 is found"),
                () -> assertTrue(result.contains(priceEntityMapper.toPrice(priceEntity2)), "Price 2 is found"),
                () -> assertTrue(result.contains(priceEntityMapper.toPrice(priceEntity3)), "Price 3 is found"));
    }
}