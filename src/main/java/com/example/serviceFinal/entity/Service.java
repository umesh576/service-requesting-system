package com.example.serviceFinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import jakarta.annotation.Nullable;
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
@Table(name = "service")
public class Service {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer Id;

  @Column(name = "serviceName")
  @NotBlank(message = "Service name is must important")
  @Size(
    min = 3,
    max = 200,
    message = "Service name must be more than 3 character and less than 200 character"
  )
  private String serviceName;

  @Column(name = "description")
  @NotBlank(message = "Description is mandatory.")
  @Size(
    min = 20,
    max = 5000,
    message = "Description name must be more than 20 character and less than 5000 character"
  )
  private String description;

  @Column(name = "serviceImage")
  private String serviceImage;

  // Many-to-One relationship with Location
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  /*(name = "location_id", nullable = false)*/
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  // @NotNull(message = "Location is required")
  private Location location;

  @Transient // This field won't be persisted in DB
  private Integer locationId;

  // Custom setter for locationId
  public void setLocationId(Integer locationId) {
    this.locationId = locationId;
  }

  // Get locationId from the Location object
  public Integer getLocationId() {
    return this.location != null ? this.location.getId() : null;
  }

  @Column(name = "price")
  // @NotBlank(message = "Price is mandatory")
  @NotNull(message = "Price is mandatory.")
  private Double price;

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Location getLocation() {
    return location;
  }

  public Service() {}

  public Integer getId() {
    return Id;
  }

  public void setId(Integer id) {
    Id = id;
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

  @Override
  public String toString() {
    return (
      "Service [Id=" +
      Id +
      ", serviceName=" +
      serviceName +
      ", description=" +
      description +
      ", serviceImage=" +
      serviceImage +
      "]" +
      "price=[" +
      price +
      "]"
    );
  }
}
