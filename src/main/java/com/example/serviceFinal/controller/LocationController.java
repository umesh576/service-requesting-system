package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.Location;
import com.example.serviceFinal.repository.LocationRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

  @Autowired
  private LocationRepository locationRepository;

  @PostMapping("/add")
  public ResponseEntity<?> createLocation(@RequestBody Location location) {
    try {
      // Validate input
      if (
        location.getLocation() == null ||
        location.getLocation().trim().isEmpty()
      ) {
        return ResponseEntity.badRequest()
          .body(Map.of("error", "Location name is required"));
      }

      // Trim and normalize
      String locationName = location.getLocation().trim();

      // Check if location already exists (case-insensitive)
      boolean locationExists = locationRepository.existsByLocationIgnoreCase(
        locationName
      );
      if (locationExists) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
          Map.of("error", "Location '" + locationName + "' already exists")
        );
      }

      // Set trimmed name
      location.setLocation(locationName);

      // Save location
      Location savedLocation = locationRepository.save(location);

      return ResponseEntity.status(HttpStatus.CREATED).body(
        Map.of(
          "message",
          "Location created successfully",
          "location",
          savedLocation
        )
      );
    } catch (DataIntegrityViolationException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(
        Map.of("error", "Location already exists")
      );
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        Map.of("error", "Failed to create location")
      );
    }
  }

  // Optional: Add a GET endpoint to check if location exists
  @GetMapping("/check/{locationName}")
  public ResponseEntity<?> checkLocationExists(
    @PathVariable String locationName
  ) {
    boolean exists = locationRepository.existsByLocationIgnoreCase(
      locationName.trim()
    );
    return ResponseEntity.ok(Map.of("exists", exists));
  }
}
