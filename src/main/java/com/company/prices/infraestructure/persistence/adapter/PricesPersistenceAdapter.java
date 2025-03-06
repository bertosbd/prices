package com.company.prices.infraestructure.persistence.adapter;

import com.company.prices.domain.entity.Price;
import com.company.prices.domain.port.PricesPersistencePort;
import com.company.prices.infraestructure.persistence.jpa.PricesRepository;
import com.company.prices.infraestructure.persistence.mappers.PriceEntityMapper;
import com.company.prices.infraestructure.persistence.model.PriceEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PricesPersistenceAdapter implements PricesPersistencePort {

    private final PricesRepository pricesRepository;
    private final PriceEntityMapper pricesEntityMapper;


    @Override
    public List<Price> getPricesByProductBrandAndDate(Integer idProduct, Integer idBrand, LocalDateTime date) {

        List<PriceEntity> prices = pricesRepository.getPricesByProductBrandAndDate(idProduct, idBrand, date);
        return pricesEntityMapper.toPriceList(prices);
    }


}
