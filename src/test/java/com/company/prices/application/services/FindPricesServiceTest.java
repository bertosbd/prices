package com.company.prices.application.services;

import com.company.prices.domain.entity.Price;
import com.company.prices.domain.port.PricesPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindPricesServiceTest {

    @Mock
    PricesPersistencePort pricesPersistencePort;

    @InjectMocks
    private FindPricesService findPricesService;

    @Test
    void givenSearchCriteria_whenOnePriceFound_thenPriceIsReturn() {
        Price price = new Price(1, LocalDateTime.now(), LocalDateTime.now(), 1, 1,
                1, BigDecimal.ONE, "EUR");

        when(pricesPersistencePort.getPricesByProductBrandAndDate(any(), any(), any()))
                .thenReturn(List.of(price));

        Optional<Price> resultPrice = findPricesService.getProductPriceByBrandAndDateWithMaxPriority(
                1, 1, LocalDateTime.now());

        assertAll("Search price by product, brand and date",
                () -> assertTrue(resultPrice.isPresent(), "Price found"),
                () -> assertEquals(price, resultPrice.get(), "Price is the expected"));
    }

    @Test
    void givenSearchCriteria_whenMoreThanOnePriceFound_thenPriceWithMaxPriorityIsReturned() {

        Price price1 = new Price(1, LocalDateTime.now(), LocalDateTime.now(), 1, 1,
                1, BigDecimal.ZERO, "EUR");
        Price price2 = new Price(1, LocalDateTime.now(), LocalDateTime.now(), 1, 1,
                2, BigDecimal.ONE, "EUR");

        when(pricesPersistencePort.getPricesByProductBrandAndDate(any(), any(), any()))
                .thenReturn(List.of(price1, price2));

        Optional<Price> resultPrice = findPricesService.getProductPriceByBrandAndDateWithMaxPriority(
                1, 1, LocalDateTime.now());

        assertAll("Search price by product, brand and date and get max priority price",
                () -> assertTrue(resultPrice.isPresent(), "Price found"),
                () -> assertEquals(price2, resultPrice.get(), "Price with max priority is the expected"));
    }

    @Test
    void givenSearchCriteria_whenNoPriceFound_thenEmptyPriceIsReturned() {

        when(pricesPersistencePort.getPricesByProductBrandAndDate(any(), any(), any()))
                .thenReturn(Collections.emptyList());

        assertTrue(findPricesService.getProductPriceByBrandAndDateWithMaxPriority(1, 1,
                LocalDateTime.now()).isEmpty());

    }
}