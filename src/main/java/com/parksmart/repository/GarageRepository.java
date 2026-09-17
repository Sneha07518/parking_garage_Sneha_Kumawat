package com.parksmart.repository;
import org.springframework.data.jpa.repository.JpaRepository; import com.parksmart.entity.Garage;
public interface GarageRepository extends JpaRepository<Garage,Long>{}