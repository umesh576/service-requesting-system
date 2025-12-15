package com.example.serviceFinal.repository;

import com.example.serviceFinal.entity.Service;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
  // Find all services by location id
  List<Service> findByLocationId(Integer locationId);

  // Find all services by location name
  List<Service> findByLocationLocation(String locationName);

  // Check if service exists by name in a specific location
  boolean existsByServiceNameAndLocationId(
    String serviceName,
    Integer locationId
  );
}
