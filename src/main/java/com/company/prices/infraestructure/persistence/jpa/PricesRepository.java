package com.company.prices.infraestructure.persistence.jpa;

import com.company.prices.infraestructure.persistence.model.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PricesRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p WHERE p.productId = :idProduct AND p.brandId = :idBrand " +
            "AND :date between p.startDate AND p.endDate")
    List<PriceEntity> getPricesByProductBrandAndDate(@Param("idProduct") Integer idProduct, @Param("idBrand") Integer idBrand,
                                                     @Param("date") LocalDateTime date);
}
