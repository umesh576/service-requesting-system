package com.example.serviceFinal.controller;

import com.example.serviceFinal.dto.ServiceRequestDTO;
import com.example.serviceFinal.entity.Service;
import com.example.serviceFinal.service.CloudinaryService;
import com.example.serviceFinal.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private CloudinaryService cloudinaryService;

    // Create service with image upload
    @PostMapping("/create")
    public ResponseEntity<?> createService(
            @RequestParam("serviceName") String serviceName,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("locationId") Integer locationId,
            @RequestParam(value = "image", required = false) MultipartFile serviceImage) {
        
        try {
            String imageUrl = null;
            
            // Upload image if provided
            if (serviceImage != null && !serviceImage.isEmpty()) {
                try {
                    imageUrl = cloudinaryService.uploadFile(serviceImage, "services");
                } catch (IOException e) {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Image upload failed: " + e.getMessage());
                    return ResponseEntity.badRequest().body(error);
                }
            }
            
            // Create DTO
            ServiceRequestDTO serviceDTO = new ServiceRequestDTO();
            serviceDTO.setServiceName(serviceName);
            serviceDTO.setDescription(description);
            serviceDTO.setPrice(price);
            serviceDTO.setLocationId(locationId);
            serviceDTO.setServiceImage(imageUrl);
            
            // Create service
            Service savedService = serviceService.createServiceWithImage(serviceDTO, imageUrl);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedService);
            
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Get all services
    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        List<Service> services = serviceService.getAllServicesWithLocations();
        return ResponseEntity.ok(services);
    }

    // Get service by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceById(@PathVariable Integer id) {
        try {
            Service service = serviceService.getServiceById(id);
            return ResponseEntity.ok(service);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Get services by location ID
    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<Service>> getServicesByLocation(@PathVariable Integer locationId) {
        List<Service> services = serviceService.getServicesByLocationId(locationId);
        return ResponseEntity.ok(services);
    }

    // Get services by location name
    @GetMapping("/location/name/{locationName}")
    public ResponseEntity<List<Service>> getServicesByLocationName(@PathVariable String locationName) {
        List<Service> services = serviceService.getServicesByLocationName(locationName);
        return ResponseEntity.ok(services);
    }

    // Update service
    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(
            @PathVariable Integer id,
            @Valid @RequestBody ServiceRequestDTO serviceDTO) {
        try {
            Service updatedService = serviceService.updateService(id, serviceDTO);
            return ResponseEntity.ok(updatedService);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Delete service
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Integer id) {
        try {
            serviceService.deleteService(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Service deleted successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Check if service exists in location
    @GetMapping("/exists")
    public ResponseEntity<Map<String, Boolean>> checkServiceExists(
            @RequestParam String serviceName,
            @RequestParam Integer locationId) {
        boolean exists = serviceService.serviceExistsInLocation(serviceName, locationId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
}