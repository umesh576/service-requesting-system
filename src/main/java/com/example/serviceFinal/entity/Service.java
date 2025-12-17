package com.example.serviceFinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "services") // Changed to "services" to avoid SQL keyword conflict
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer id;

    @Column(name = "service_name", nullable = false)
    @NotBlank(message = "Service name is mandatory")
    @Size(min = 3, max = 200, message = "Service name must be between 3 and 200 characters")
    private String serviceName;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotBlank(message = "Description is mandatory")
    @Size(min = 20, max = 5000, message = "Description must be between 20 and 5000 characters")
    private String description;

    @Column(name = "service_image")
    private String serviceImage;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Price is mandatory")
    private Double price;

    // Many-to-One relationship with Location
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull(message = "Location is required")
    private Location location;

    @Transient // This field won't be persisted in DB, used for DTO purposes
    private Integer locationId;

    // Constructors
    public Service() {}

    public Service(String serviceName, String description, Double price, Location location) {
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.location = location;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // Custom getter for locationId
    public Integer getLocationId() {
        return this.location != null ? this.location.getId() : this.locationId;
    }

    // Custom setter for locationId (for DTO purposes)
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "Service [id=" + id + ", serviceName=" + serviceName + ", description=" + description + 
               ", serviceImage=" + serviceImage + ", price=" + price + ", location=" + 
               (location != null ? location.getLocationName() : "null") + "]";
    }
}