package com.company.prices.infrastructure.persistence.adapter;

import com.company.prices.domain.entity.Price;
import com.company.prices.domain.port.PricesPersistencePort;
import com.company.prices.infrastructure.persistence.jpa.PricesRepository;
import com.company.prices.infrastructure.persistence.mappers.PriceEntityMapper;
import com.company.prices.infrastructure.persistence.model.PriceEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PricesPersistenceAdapter implements PricesPersistencePort {

    private final PricesRepository pricesRepository;
    private final PriceEntityMapper pricesEntityMapper;


    @Override
    public Optional<Price> getPriceByProductBrandAndDateWithMaxPriority(Integer idProduct, Integer idBrand, LocalDateTime date) {

        Optional<PriceEntity> price = pricesRepository.getPriceByProductBrandAndDateWithMaxPriority(idProduct, idBrand, date);

        return price.map(pricesEntityMapper::toPrice);
    }


}
