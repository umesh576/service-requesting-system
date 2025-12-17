package com.example.serviceFinal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ServiceRequestDTO {
    
    @NotBlank(message = "Service name is mandatory")
    @Size(min = 3, max = 200, message = "Service name must be between 3 and 200 characters")
    private String serviceName;
    
    @NotBlank(message = "Description is mandatory")
    @Size(min = 20, max = 5000, message = "Description must be between 20 and 5000 characters")
    private String description;
    
    @NotNull(message = "Price is mandatory")
    private Double price;
    
    @NotNull(message = "Location ID is required")
    private Integer locationId;
    
    private String serviceImage;
    
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
    
    public String getServiceImage() {
        return serviceImage;
    }
    
    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }
}