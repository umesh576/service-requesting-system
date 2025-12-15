package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.Service;
import com.example.serviceFinal.repository.ServiceRepository;
import com.example.serviceFinal.service.CloudinaryService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/service")
public class ServiceController {

  @Autowired
  private ServiceRepository servicerepositry;

  @Autowired
  private CloudinaryService cloudinaryservice;

  // @PostMapping("/create")
  // public ResponseEntity<?> CreateService(
  //   @RequestParam("serviceName") String serviceName,
  //   @RequestParam("description") String description,
  //   @RequestParam(value = "image", required = false) MultipartFile serviceImage
  // ) {
  //   Service service = new Service();
  //   service.setServiceName(serviceName);
  //   service.setDescription(description); // Upload image if provided
  //   if (serviceImage != null && !serviceImage.isEmpty()) {
  //     try {
  //       String imageUrl = cloudinaryservice.uploadFile(
  //         serviceImage,
  //         "services"
  //       );
  //       service.setServiceImage(imageUrl);
  //     } catch (IOException e) {
  //       return ResponseEntity.badRequest()
  //         .body("Image upload failed: " + e.getMessage());
  //     }
  //   }

  //   Service savedService = servicerepositry.save(service);
  //   return ResponseEntity.ok(savedService);
  // }
  @PostMapping("/create")
  public ResponseEntity<?> CreateService(
    @RequestParam("serviceName") String serviceName,
    @RequestParam("description") String description,
    @RequestParam("price") Double price,
    @RequestParam("locationId") Integer locationId,
    @RequestParam(value = "image", required = false) MultipartFile serviceImage
  ) {
    Service service = new Service();
    service.setServiceName(serviceName);
    service.setDescription(description);
    service.setPrice(price);

    // Set location (you'll need to fetch it from repository)
    // Location location = locationRepository.findById(locationId).orElseThrow();
    // service.setLocation(location);

    // Upload image if provided
    if (serviceImage != null && !serviceImage.isEmpty()) {
      try {
        String imageUrl = cloudinaryservice.uploadFile(
          serviceImage,
          "services"
        );
        service.setServiceImage(imageUrl);
      } catch (IOException e) {
        return ResponseEntity.badRequest()
          .body("Image upload failed: " + e.getMessage());
      }
    }

    Service savedService = servicerepositry.save(service);
    return ResponseEntity.ok(savedService);
  }
}
