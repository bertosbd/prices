package com.company.prices.infraestructure.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponse {

    private String idProducto;
    private String idBrand;
    private String priceList;
    private LocalDateTime priceStartDate;
    private LocalDateTime priceEndDate;
    private BigDecimal price;


}
