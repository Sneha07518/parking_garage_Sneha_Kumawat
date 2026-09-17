package com.parksmart.repository;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository; import com.parksmart.entity.*;
public interface RateCardRepository extends JpaRepository<RateCard,Long>{List<RateCard> findByGarageId(Long garageId);Optional<RateCard> findByGarageIdAndSpotType(Long garageId,SpotType type);}