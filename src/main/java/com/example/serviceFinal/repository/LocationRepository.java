package com.example.serviceFinal.repository;

import com.example.serviceFinal.entity.Location;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer> {
  // Check if location exists by name (case-insensitive)
  boolean existsByLocationIgnoreCase(String location);

  // Find location by name (exact match)
  Optional<Location> findByLocation(String location);

  // Find location by name (case-insensitive)
  Optional<Location> findByLocationIgnoreCase(String location);
}
