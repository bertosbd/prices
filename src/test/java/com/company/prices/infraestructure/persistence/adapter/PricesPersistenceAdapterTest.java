package com.company.prices.infraestructure.persistence.adapter;

import com.company.prices.domain.entity.Price;
import com.company.prices.infraestructure.persistence.jpa.PricesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @InjectMocks
    PricesPersistenceAdapter pricesPersistenceAdapter;

    @Test
    void givenPriceCriteria_whenPriceFound_thenPriceIsReturned() {
        Price price2 = new Price(1, 1, LocalDateTime.now(), LocalDateTime.now(), 1, 1,
                2, BigDecimal.ONE, "EUR", null);

        when(pricesRepository.getPricesByProductBrandAndDate(any(), any(), any()))
                .thenReturn(List.of(price2));

        List<Price> result = pricesPersistenceAdapter.
                getPricesByProductBrandAndDate(1, 1, LocalDateTime.now());

        assertAll("Search price by product, brand and date and get max priority price",
                () -> assertFalse(result.isEmpty(), "Price found"),
                () -> assertEquals(1, result.size(), "Price with max priority is the expected"),
                () -> assertEquals(price2, result.get(0), "Expected price is found"));
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
        Price price1 = new Price(1, 1, LocalDateTime.now(), LocalDateTime.now(), 1, 1,
                2, BigDecimal.ONE, "EUR", null);
        Price price2 = new Price(1, 1, LocalDateTime.now(), LocalDateTime.now(), 1, 1,
                2, BigDecimal.ONE, "EUR", null);
        Price price3 = new Price(1, 1, LocalDateTime.now(), LocalDateTime.now(), 1, 1,
                3, BigDecimal.ONE, "EUR", null);

        when(pricesRepository.getPricesByProductBrandAndDate(any(), any(), any()))
                .thenReturn(List.of(price1, price2, price3));

        List<Price> result = pricesPersistenceAdapter.
                getPricesByProductBrandAndDate(1, 1, LocalDateTime.now());

        assertAll("Search price by product, brand and datee",
                () -> assertFalse(result.isEmpty(), "Prices found"),
                () -> assertEquals(3, result.size(), "All expected prices are returned"),
                () -> assertTrue(result.contains(price1), "Price 1 is found"),
                () -> assertTrue(result.contains(price2), "Price 2 is found"),
                () -> assertTrue(result.contains(price3), "Price 3 is found"));
    }
}