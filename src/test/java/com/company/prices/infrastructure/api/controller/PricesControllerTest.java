package com.company.prices.infrastructure.api.controller;

import com.company.prices.domain.entity.Price;
import com.company.prices.domain.usecase.FindPrices;
import com.company.prices.infrastructure.api.dto.PriceResponse;
import com.company.prices.infrastructure.api.exception.PriceNotFoundException;
import com.company.prices.infrastructure.api.mappers.PricesMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PricesController.class)
class PricesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    FindPrices findPrices;

    @MockitoBean
    PricesMapper pricesMapper;

    @Test
    void givenGetPriceRequest_whenPricesIsFound_thenPricesIsReturnedWithOkStatus() throws Exception {

        when(findPrices.getProductPriceByBrandAndDateWithMaxPriority(any(), any(), any()))
                .thenReturn(Optional.of(new Price()));

        when(pricesMapper.toPriceResponse(any())).thenReturn(new PriceResponse());

        mockMvc.perform(get("/prices/product/1?brandId=1&priceDate=2020-06-14T10:00:00"))
                .andExpect(status().isOk());

    }

    @Test
    void givenGetPriceRequest_whenPricesNotFound_thenPricesIsReturnedWithNotFoundStatusAndMessageContent() throws Exception {

        Integer productId = 1;
        Integer brandId = 1;
        LocalDateTime priceDate = LocalDateTime.now();

        when(findPrices.getProductPriceByBrandAndDateWithMaxPriority(any(), any(), any()))
                .thenThrow(new PriceNotFoundException(productId, brandId, priceDate));

        mockMvc.perform(get("/prices/product/{productId}", productId)
                        .param("brandId", brandId.toString())
                        .param("priceDate", priceDate.toString()))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$").value("Price not found for product " + productId +
                                ", brand " + brandId + " and date " + priceDate));
    }

    @Test
    void givenGetPriceRequest_whenWrongProductIdFormat_thenBadRequestIsReturnWithErrorInformation() throws Exception {

        String productId = "wrongProductId";
        int brandId = 1;
        LocalDateTime priceDate = LocalDateTime.now();

        mockMvc.perform(get("/prices/product/{productId}", productId)
                        .param("brandId", Integer.toString(brandId))
                        .param("priceDate", priceDate.toString()))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.message").value("Invalid parameter type"),
                        jsonPath("$.details[0].field").value("productId"),
                        jsonPath("$.details[0].message")
                                .value("Received " + productId + " (String). Expected type Integer"));
    }

    @Test
    void givenGetPriceRequest_whenWrongBrandIdFormat_thenBadRequestIsReturnWithErrorInformation() throws Exception {

        int productId = 1;
        String brandId = "1a";
        LocalDateTime priceDate = LocalDateTime.now();

        mockMvc.perform(get("/prices/product/{productId}", productId)
                        .param("brandId", brandId)
                        .param("priceDate", priceDate.toString()))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.message").value("Invalid parameter type"),
                        jsonPath("$.details[0].field").value("brandId"),
                        jsonPath("$.details[0].message")
                                .value("Received " + brandId + " (String). Expected type Integer"));
    }

    @Test
    void givenGetPriceRequest_whenWrongDateTimeFormat_thenBadRequestIsReturnWithErrorInformation() throws Exception {

        int productId = 1;
        int brandId = 1;
        String priceDate = "testDateTime";

        mockMvc.perform(get("/prices/product/{productId}", productId)
                        .param("brandId", String.valueOf(brandId))
                        .param("priceDate", priceDate))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.message").value("Invalid parameter type"),
                        jsonPath("$.details[0].field").value("priceDate"),
                        jsonPath("$.details[0].message")
                                .value("Received " + priceDate + " (String). Expected type LocalDateTime"));
    }

    @Test
    void givenGetPriceRequest_whenAnyParamIsNotFound_thenBadRequestIsReturnWithErrorInformation() throws Exception {

        int productId = 1;
        int brandId = 1;

        mockMvc.perform(get("/prices/product/{productId}", productId)
                        .param("brandId", String.valueOf(brandId)))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.message").value("Missing parameters"),
                        jsonPath("$.details[0].field").value("priceDate"),
                        jsonPath("$.details[0].message")
                                .value("Required request parameter 'priceDate' for method parameter type " +
                                        "LocalDateTime is not present"));
    }

    @Test
    void givenGetPriceRequest_whenUnexpectedErrorFound_thenInternalServerErrorWithErrorInformation() throws Exception {
        RuntimeException exception = new RuntimeException("And unexpected error is thrown");
        when(findPrices.getProductPriceByBrandAndDateWithMaxPriority(any(), any(), any()))
                .thenThrow(exception);

        int productId = 1;
        int brandId = 1;
        LocalDateTime priceDate = LocalDateTime.now();

        mockMvc.perform(get("/prices/product/{productId}", productId)
                        .param("brandId", String.valueOf(brandId))
                        .param("priceDate", priceDate.toString()))
                .andExpectAll(status().isInternalServerError(),
                        jsonPath("$.message").value(exception.getMessage()));
    }
}