package com.example.serviceFinal.controller;

import com.example.serviceFinal.dto.ServiceRequestDTO;
import com.example.serviceFinal.entity.Location;
import com.example.serviceFinal.entity.Service;
import com.example.serviceFinal.repository.LocationRepository;
import com.example.serviceFinal.repository.ServiceRepository;
import com.example.serviceFinal.service.CloudinaryService;
// import jakarta.ws.rs.core.MediaType;
// import com.example.serviceFinal.service.ServiceService;
// import jakarta.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
// import org.apache.tomcat.util.http.parser.MediaType;
// import javax.management.RuntimeErrorException;
// import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
// import org.springframework.http.MediaType;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {

  //   @Autowired
  //   private ServiceService serviceService;

  @Autowired
  private ServiceRepository servicerepositry;

  @Autowired
  private CloudinaryService cloudinaryService;

  @Autowired
  private LocationRepository locationRepository;

  @PostMapping(
    value = "/create",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public ResponseEntity<?> createService(
    @ModelAttribute ServiceRequestDTO serviceDTO
  ) throws IOException {
    if (
      serviceDTO.getServiceName() == null ||
      serviceDTO.getDescription() == null ||
      serviceDTO.getPrice() == null ||
      serviceDTO.getLocationId() == null ||
      serviceDTO.getImageFile() == null
    ) {
      throw new RuntimeException("Please enter all required fields");
    }

    if (
      servicerepositry
        .findByServiceName(serviceDTO.getServiceName())
        .isPresent()
    ) {
      throw new RuntimeException("Service already exists");
    }

    // ✅ Upload image ONCE
    String imageUrl = cloudinaryService.uploadFile(
      serviceDTO.getImageFile(),
      "services"
    );

    // ✅ Fetch location
    Location location = locationRepository
      .findById(serviceDTO.getLocationId())
      .orElseThrow(() -> new RuntimeException("Location not found"));

    // ✅ Create entity
    Service service = new Service();
    service.setServiceName(serviceDTO.getServiceName());
    service.setDescription(serviceDTO.getDescription());
    service.setPrice(serviceDTO.getPrice());
    service.setServiceImage(imageUrl);
    service.setLocation(location);

    return ResponseEntity.ok(servicerepositry.save(service));
  }

  // @PostMapping(
  //   value = "/create",
  //   consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
  // )
  // public ResponseEntity<?> createService(
  //   @ModelAttribute ServiceRequestDTO serviceDTO
  // ) throws IOException {
  //   if (
  //     serviceDTO.getServiceName() == null ||
  //     serviceDTO.getDescription() == null ||
  //     serviceDTO.getPrice() == null ||
  //     serviceDTO.getLocationId() == null ||
  //     serviceDTO.getImageFile() == null
  //   ) {
  //     throw new RuntimeException("Please enter all required fields");
  //   }

  //   String imageUrl = null;
  //   if (serviceDTO.getServiceImage() != null) {
  //     imageUrl = cloudinaryService.uploadFile(
  //       serviceDTO.getServiceImage(),
  //       "services"
  //     );
  //   }

  //   String serviceName = serviceDTO.getServiceName();
  //   if (servicerepositry.findByServiceName(serviceName).isPresent()) {
  //     throw new RuntimeException("Service is already present");
  //   }

  //   if (
  //     serviceDTO.getServiceImage() != null &&
  //     !serviceDTO.getServiceImage().isEmpty()
  //   ) {
  //     imageUrl = cloudinaryService.uploadFile(
  //       serviceDTO.getServiceImage(), // This is MultipartFile
  //       "services"
  //     );
  //   }

  //   // Create Service entity
  //   Service service = new Service();
  //   service.setServiceName(serviceDTO.getServiceName());
  //   service.setDescription(serviceDTO.getDescription());
  //   service.setPrice(serviceDTO.getPrice());
  //   // service.setServiceImage(imageUrl); // Store Cloudinary URL

  //   Service newService = servicerepositry.save(service);
  //   return ResponseEntity.ok(newService);
  // }

  // get all services
  @GetMapping("/")
  public List<Service> getAllLocation() {
    return (List<Service>) servicerepositry.findAll();
  }

  //Get service by id
  @GetMapping("/{id}")
  public Optional<Service> getServiceById(@PathVariable int id) {
    return servicerepositry.findById(id);
  }

  // update service api
  @PatchMapping("/update")
  public ResponseEntity<?> updateService(
    @PathVariable int id,
    @ModelAttribute Service newService
  ) {
    Optional<Service> serviceOptional = servicerepositry.findById(id);
    if (serviceOptional.isEmpty()) {
      throw new RuntimeException("Please provide service id for update.");
    }
    if (newService.getServiceName() != null) {
      newService.setServiceName(newService.getServiceName());
    }
    if (newService.getDescription() != null) {
      newService.setDescription(newService.getDescription());
    }
    if (newService.getServiceImage() != null) {
      newService.setServiceImage(newService.getServiceImage());
    }
    if (newService.getLocation() != null) {
      newService.setLocation(newService.getLocation());
    }
    if (newService.getPrice() != null) {
      newService.setPrice(newService.getPrice());
    }

    servicerepositry.save(newService);
    return ResponseEntity.ok(newService);
  }
}
