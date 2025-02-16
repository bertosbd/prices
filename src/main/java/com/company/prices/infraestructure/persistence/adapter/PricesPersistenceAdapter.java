package com.company.prices.infraestructure.persistence.adapter;

import com.company.prices.application.port.PricesPersistencePort;
import com.company.prices.domain.entity.Price;
import com.company.prices.infraestructure.persistence.jpa.PricesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PricesPersistenceAdapter implements PricesPersistencePort {

    private final PricesRepository pricesRepository;

    public PricesPersistenceAdapter(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    @Override
    public List<Price> getPricesByProductBrandAndDate(Integer idProduct, Integer idBrand, LocalDateTime date) {
        return pricesRepository.getPricesByProductBrandAndDate(idProduct, idBrand, date);
    }


}
