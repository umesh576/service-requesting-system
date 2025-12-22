package com.example.serviceFinal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class ServiceRequestDTO {

  private String serviceName;
  private String description;
  private Double price;
  private Integer locationId;

  private MultipartFile imageFile; // ✅ FILE

  //   public MultipartFile getImageFile() {
  //     return imageFile;
  //   }

  public void setImageFile(MultipartFile imageFile) {
    this.imageFile = imageFile;
  }

  // Getters and Setters
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

  public MultipartFile getServiceImage() {
    return imageFile;
  }

  public MultipartFile getImageFile() {
    return imageFile;
  }
}
