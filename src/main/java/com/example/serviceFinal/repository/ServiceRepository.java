package com.example.serviceFinal.repository;

import com.example.serviceFinal.entity.Location;
import com.example.serviceFinal.entity.Service;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
  // Essential methods for your use case:

  // 1. Find services by location ID
  List<Service> findByLocationId(Integer locationId);

  // 2. Find services by location name
  List<Service> findByLocationLocationName(String locationName);

  // 3. Check if service exists by name in a location
  boolean existsByServiceNameAndLocationId(
    String serviceName,
    Integer locationId
  );

  // 4. Find services by name containing (search)
  List<Service> findByServiceNameContaining(String name);

  // 5. Find services by price range
  List<Service> findByPriceBetween(Double minPrice, Double maxPrice);

  // 6. Count services in a location
  Long countByLocationId(Integer locationId);

  List<Service> findByLocation(Location location);
  // List<Service> findAllWithLocation();
}
