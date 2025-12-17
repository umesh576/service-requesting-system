package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.Location;
import com.example.serviceFinal.repository.LocationRepository;
import com.example.serviceFinal.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ServiceService serviceService;

    // Create a new location
    @PostMapping
    public ResponseEntity<?> createLocation(@Valid @RequestBody Location location) {
        try {
            // Check if location already exists
            if (locationRepository.existsByLocationNameIgnoreCase(location.getLocationName())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Location with name '" + location.getLocationName() + "' already exists");
                return ResponseEntity.badRequest().body(error);
            }
            
            Location savedLocation = locationRepository.save(location);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLocation);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create location: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Get all locations
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return ResponseEntity.ok(locations);
    }

    // Get location by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getLocationById(@PathVariable Integer id) {
        Optional<Location> location = locationRepository.findById(id);
        
        if (location.isPresent()) {
            return ResponseEntity.ok(location.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Location not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // Get location by ID with services
    @GetMapping("/{id}/with-services")
    public ResponseEntity<?> getLocationWithServices(@PathVariable Integer id) {
        Optional<Location> location = locationRepository.findByIdWithServices(id);
        
        if (location.isPresent()) {
            return ResponseEntity.ok(location.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Location not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // Update location
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable Integer id, @Valid @RequestBody Location locationDetails) {
        try {
            Optional<Location> locationOptional = locationRepository.findById(id);
            
            if (locationOptional.isPresent()) {
                Location location = locationOptional.get();
                
                // Update fields
                if (locationDetails.getLocationName() != null) {
                    // Check if new name already exists (excluding current location)
                    if (!locationDetails.getLocationName().equalsIgnoreCase(location.getLocationName()) &&
                        locationRepository.existsByLocationNameIgnoreCase(locationDetails.getLocationName())) {
                        Map<String, String> error = new HashMap<>();
                        error.put("error", "Location with name '" + locationDetails.getLocationName() + "' already exists");
                        return ResponseEntity.badRequest().body(error);
                    }
                    location.setLocationName(locationDetails.getLocationName());
                }
                if (locationDetails.getAddress() != null) {
                    location.setAddress(locationDetails.getAddress());
                }
                if (locationDetails.getCity() != null) {
                    location.setCity(locationDetails.getCity());
                }
                
                Location updatedLocation = locationRepository.save(location);
                return ResponseEntity.ok(updatedLocation);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Location not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to update location: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Delete location
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Integer id) {
        try {
            Optional<Location> locationOptional = locationRepository.findByIdWithServices(id);
            
            if (locationOptional.isPresent()) {
                Location location = locationOptional.get();
                
                // Check if location has services
                if (!location.getServices().isEmpty()) {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Cannot delete location that has services. Please delete services first.");
                    return ResponseEntity.badRequest().body(error);
                }
                
                locationRepository.deleteById(id);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Location deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Location not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete location: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Get all services for a location
    @GetMapping("/{id}/services")
    public ResponseEntity<?> getServicesForLocation(@PathVariable Integer id) {
        if (!locationRepository.existsById(id)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Location not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        List<com.example.serviceFinal.entity.Service> services = serviceService.getServicesByLocationId(id);
        return ResponseEntity.ok(services);
    }
}