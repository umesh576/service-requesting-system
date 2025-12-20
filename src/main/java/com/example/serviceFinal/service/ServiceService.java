// package com.example.serviceFinal.service;

// import com.example.serviceFinal.dto.ServiceRequestDTO;
// import com.example.serviceFinal.entity.Location;
// import com.example.serviceFinal.entity.Service;
// import com.example.serviceFinal.repository.LocationRepository;
// import com.example.serviceFinal.repository.ServiceRepository;
// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// // import java.util.Optional;

// @org.springframework.stereotype.Service
// @Transactional
// public class ServiceService {

//   @Autowired
//   private ServiceRepository serviceRepository;

//   @Autowired
//   private LocationRepository locationRepository;

//   // Create a new service using DTO
//   public Service createService(ServiceRequestDTO serviceDTO) {
//     // Validate location exists
//     Location location = locationRepository
//       .findById(serviceDTO.getLocationId())
//       .orElseThrow(() ->
//         new IllegalArgumentException(
//           "Location with ID " + serviceDTO.getLocationId() + " not found"
//         )
//       );

//     // Check if service already exists in this location
//     boolean serviceExists = serviceRepository.existsByServiceNameAndLocationId(
//       serviceDTO.getServiceName().trim(),
//       serviceDTO.getLocationId()
//     );

//     if (serviceExists) {
//       throw new IllegalArgumentException(
//         "Service '" +
//         serviceDTO.getServiceName() +
//         "' already exists in this location"
//       );
//     }

//     // Create new service
//     Service service = new Service();
//     service.setServiceName(serviceDTO.getServiceName());
//     service.setDescription(serviceDTO.getDescription());
//     service.setPrice(serviceDTO.getPrice());
//     service.setServiceImage(serviceDTO.getServiceImage());
//     service.setLocation(location);

//     return serviceRepository.save(service);
//   }

//   // Create service with Multipart file
//   public Service createServiceWithImage(
//     ServiceRequestDTO serviceDTO,
//     String imageUrl
//   ) {
//     Location location = locationRepository
//       .findById(serviceDTO.getLocationId())
//       .orElseThrow(() ->
//         new IllegalArgumentException(
//           "Location with ID " + serviceDTO.getLocationId() + " not found"
//         )
//       );

//     // Check if service already exists
//     boolean serviceExists = serviceRepository.existsByServiceNameAndLocationId(
//       serviceDTO.getServiceName().trim(),
//       serviceDTO.getLocationId()
//     );

//     if (serviceExists) {
//       throw new IllegalArgumentException(
//         "Service '" +
//         serviceDTO.getServiceName() +
//         "' already exists in this location"
//       );
//     }

//     Service service = new Service();
//     service.setServiceName(serviceDTO.getServiceName());
//     service.setDescription(serviceDTO.getDescription());
//     service.setPrice(serviceDTO.getPrice());
//     service.setServiceImage(imageUrl);
//     service.setLocation(location);

//     return serviceRepository.save(service);
//   }

//   // Get all services for a location
//   public List<Service> getServicesByLocationId(Integer locationId) {
//     return serviceRepository.findByLocationId(locationId);
//   }

//   // Get all services with their locations
//   public List<Service> getAllServicesWithLocations() {
//     List<Service> service = serviceRepository.findAll();

//     return (List<Service>) ((Service) service).getLocation();
//   }

//   // Get service by ID with location
//   public Service getServiceById(Integer id) {
//     return serviceRepository
//       .findById(id)
//       .orElseThrow(() ->
//         new IllegalArgumentException("Service not found with ID: " + id)
//       );
//   }

//   // Get services by location name
//   public List<Service> getServicesByLocationName(String locationName) {
//     return serviceRepository.findByLocationLocationName(locationName);
//   }

//   // Update service
//   public Service updateService(Integer id, ServiceRequestDTO serviceDTO) {
//     Service service = serviceRepository
//       .findById(id)
//       .orElseThrow(() ->
//         new IllegalArgumentException("Service not found with ID: " + id)
//       );

//     // Update fields if provided
//     if (serviceDTO.getServiceName() != null) {
//       service.setServiceName(serviceDTO.getServiceName());
//     }
//     if (serviceDTO.getDescription() != null) {
//       service.setDescription(serviceDTO.getDescription());
//     }
//     if (serviceDTO.getPrice() != null) {
//       service.setPrice(serviceDTO.getPrice());
//     }
//     if (serviceDTO.getServiceImage() != null) {
//       service.setServiceImage(serviceDTO.getServiceImage());
//     }

//     // Update location if provided
//     if (
//       serviceDTO.getLocationId() != null &&
//       !serviceDTO.getLocationId().equals(service.getLocation().getId())
//     ) {
//       Location newLocation = locationRepository
//         .findById(serviceDTO.getLocationId())
//         .orElseThrow(() ->
//           new IllegalArgumentException(
//             "Location with ID " + serviceDTO.getLocationId() + " not found"
//           )
//         );
//       service.setLocation(newLocation);
//     }

//     return serviceRepository.save(service);
//   }

//   // Delete service
//   public void deleteService(Integer id) {
//     if (!serviceRepository.existsById(id)) {
//       throw new IllegalArgumentException("Service not found with ID: " + id);
//     }
//     serviceRepository.deleteById(id);
//   }

//   // Check if service exists in location
//   public boolean serviceExistsInLocation(
//     String serviceName,
//     Integer locationId
//   ) {
//     return serviceRepository.existsByServiceNameAndLocationId(
//       serviceName,
//       locationId
//     );
//   }
// }
