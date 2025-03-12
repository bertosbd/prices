package com.company.prices.infrastructure.persistence.jpa;

import com.company.prices.infrastructure.persistence.model.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PricesRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p WHERE p.productId = :idProduct AND p.brandId = :idBrand " +
            "AND :date between p.startDate AND p.endDate ORDER BY p.priority DESC LIMIT 1")
    Optional<PriceEntity> getPriceByProductBrandAndDateWithMaxPriority(@Param("idProduct") Integer idProduct,
                                                                       @Param("idBrand") Integer idBrand,
                                                                       @Param("date") LocalDateTime date);
}
