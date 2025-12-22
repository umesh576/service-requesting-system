package com.example.serviceFinal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class ServiceRequestDTO {

  @NotBlank(message = "Service name is mandatory")
  @Size(min = 3, max = 200)
  private String serviceName;

  @NotBlank(message = "Description is mandatory")
  @Size(min = 20, max = 5000)
  private String description;

  @NotNull(message = "Price is mandatory")
  private Double price;

  @NotNull(message = "Location ID is required")
  private Integer locationId;

  @NotNull(message = "Service image is required")
  private MultipartFile imageFile; // ✅ Must be MultipartFile

  // getters & setters
  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getLocationId() {
    return locationId;
  }

  public void setLocationId(Integer locationId) {
    this.locationId = locationId;
  }

  public MultipartFile getImageFile() {
    return imageFile;
  }

  public void setImageFile(MultipartFile imageFile) {
    this.imageFile = imageFile;
  }
}
