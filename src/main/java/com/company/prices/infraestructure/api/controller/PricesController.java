package com.company.prices.infraestructure.api.controller;

import com.company.prices.application.usecase.FindPrices;
import com.company.prices.domain.entity.Price;
import com.company.prices.infraestructure.api.dto.PriceResponse;
import com.company.prices.infraestructure.api.dto.errors.ErrorResponse;
import com.company.prices.infraestructure.api.exception.PriceNotFoundException;
import com.company.prices.infraestructure.api.mappers.PricesMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/prices")

public class PricesController {

    private final FindPrices findPrices;

    private final PricesMapper pricesMapper;

    @Autowired
    public PricesController(FindPrices findPrices, PricesMapper pricesMapper) {
        this.findPrices = findPrices;
        this.pricesMapper = pricesMapper;
    }

    @Operation(summary = "Get product price by brandId and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price found"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse[].class))}
            ),
            @ApiResponse(responseCode = "404", description = "Price not found")
    })

    @GetMapping("/product/{productId}")
    public ResponseEntity<PriceResponse> getProductPriceByBrandAndDate(@Parameter(description = "Product identifier",
                                                                               required = true)
                                                                       @PathVariable("productId") @NotNull @Min(1) Integer productId,
                                                                       @Parameter(description = "Product brandId",
                                                                               required = true)
                                                                       @RequestParam @NotNull @Min(1) Integer brandId,
                                                                       @Parameter(description = "Price date",
                                                                               required = true,
                                                                               example = "2020-12-31T13:25:11")
                                                                       @RequestParam @NotNull
                                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                       LocalDateTime priceDate) throws PriceNotFoundException {

        Optional<Price> price = findPrices.getProductPriceByBrandAndDateWithMaxPriority(productId, brandId, priceDate);

        return price.map(p -> ResponseEntity.status(HttpStatus.OK).body(pricesMapper.toPriceResponse(p)))
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, priceDate));

    }

}
