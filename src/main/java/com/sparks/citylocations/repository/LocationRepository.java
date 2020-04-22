package com.sparks.citylocations.repository;



import com.sparks.citylocations.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository<Location, Long> {
 }
