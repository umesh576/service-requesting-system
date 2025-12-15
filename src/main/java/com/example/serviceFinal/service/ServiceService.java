package com.example.serviceFinal.service;

import com.example.serviceFinal.entity.Location;
import com.example.serviceFinal.entity.Service;
import com.example.serviceFinal.repository.LocationRepository;
import com.example.serviceFinal.repository.ServiceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

// import org.springframework.stereotype.Service;

@org.springframework.stereotype.Service
public class ServiceService {

  @Autowired
  private ServiceRepository serviceRepository;

  @Autowired
  private LocationRepository locationRepository;

  // Create a new service with location_id
  public Service createService(Service service, Integer locationId) {
    // Find the location
    Optional<Location> locationOptional = locationRepository.findById(
      locationId
    );

    if (locationOptional.isEmpty()) {
      throw new IllegalArgumentException(
        "Location with ID " + locationId + " not found"
      );
    }

    // Check if service already exists in this location
    boolean serviceExists = serviceRepository.existsByServiceNameAndLocationId(
      service.getServiceName().trim(),
      locationId
    );

    if (serviceExists) {
      throw new IllegalArgumentException(
        "Service '" +
        service.getServiceName() +
        "' already exists in this location"
      );
    }

    // Set the location
    service.setLocation(locationOptional.get());

    // Save the service
    return serviceRepository.save(service);
  }

  // Get all services for a location
  public List<Service> getServicesByLocationId(Integer locationId) {
    return serviceRepository.findByLocationId(locationId);
  }

  // Get service by ID
  public Optional<Service> getServiceById(Integer id) {
    return serviceRepository.findById(id);
  }

  // Update service
  public Service updateService(Integer id, Service serviceDetails) {
    Optional<Service> serviceOptional = serviceRepository.findById(id);

    if (serviceOptional.isEmpty()) {
      throw new IllegalArgumentException("Service not found with ID: " + id);
    }

    Service service = serviceOptional.get();

    // Update fields
    if (serviceDetails.getServiceName() != null) {
      service.setServiceName(serviceDetails.getServiceName());
    }
    if (serviceDetails.getDescription() != null) {
      service.setDescription(serviceDetails.getDescription());
    }
    // if (serviceDetails.getPrice() != null) {
    //   service.setPrice(serviceDetails.getPrice());
    // }

    return serviceRepository.save(service);
  }

  // Delete service
  public void deleteService(Integer id) {
    if (!serviceRepository.existsById(id)) {
      throw new IllegalArgumentException("Service not found with ID: " + id);
    }
    serviceRepository.deleteById(id);
  }
}
