package com.company.prices.infrastructure.api.mappers;

import com.company.prices.domain.entity.Price;
import com.company.prices.infrastructure.api.dto.PriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface PricesMapper {

    @Mapping(target = "idProducto", source = "productId")
    @Mapping(target = "idBrand", source = "brandId")
    @Mapping(target = "priceList", source = "priceList")
    @Mapping(target = "priceStartDate", source = "startDate")
    @Mapping(target = "priceEndDate", source = "endDate")
    @Mapping(target = "price", source = "price")
    PriceResponse toPriceResponse(Price price);

}
