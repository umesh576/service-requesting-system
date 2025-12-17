package com.example.serviceFinal.repository;

import com.example.serviceFinal.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    
    // Check if location exists by name (case-insensitive)
    boolean existsByLocationNameIgnoreCase(String locationName);
    
    // Find location by name (exact match)
    Optional<Location> findByLocationName(String locationName);
    
    // Find location by name (case-insensitive)
    Optional<Location> findByLocationNameIgnoreCase(String locationName);
    
    // Find location with its services
    @Query("SELECT l FROM Location l LEFT JOIN FETCH l.services WHERE l.id = :id")
    Optional<Location> findByIdWithServices(@Param("id") Integer id);
}