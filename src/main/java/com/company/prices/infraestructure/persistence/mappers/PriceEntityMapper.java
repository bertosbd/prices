package com.company.prices.infraestructure.persistence.mappers;

import com.company.prices.domain.entity.Price;
import com.company.prices.infraestructure.persistence.model.PriceEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface PriceEntityMapper {

    Price toPrice(PriceEntity price);

    List<Price> toPriceList(List<PriceEntity> prices);

}
