package com.company.prices.infrastructure.persistence.mappers;

import com.company.prices.domain.entity.Price;
import com.company.prices.infrastructure.persistence.model.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface PriceEntityMapper {

    Price toPrice(PriceEntity price);

}
