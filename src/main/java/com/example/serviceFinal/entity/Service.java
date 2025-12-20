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
import jakarta.persistence.UniqueConstraint;

// import jakarta.persistence.Transient;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Size;

@Entity
@Table(
  name = "services",
  uniqueConstraints = @UniqueConstraint(columnNames = "serviceName")
)
public class Service {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "service_id")
  private Integer id;

  @Column(name = "service_name", nullable = false)
  private String serviceName;

  @Column(columnDefinition = "TEXT", name = "description")
  private String description;

  @Column(name = "serviceImage")
  private String serviceImage;

  @Column(nullable = false)
  private Double price;

  // @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn(name = "location_id", nullable = false)
  // @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  // private Location location;

  @Column(name = "location")
  private int location;

  // Constructors
  public Service() {}

  public Service(
    String serviceName,
    String description,
    Double price,
    Integer location
  ) {
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

  public Integer getLocation() {
    return location;
  }

  public void setLocation(Integer location) {
    this.location = location;
  }

  // Custom getter for locationId
  // public Integer getLocationId() {
  //     return this.location != null ? this.location.getId() : this.locationId;
  // }

  // // Custom setter for locationId (for DTO purposes)
  // public void setLocationId(Integer locationId) {
  //     this.locationId = locationId;
  // }

  @Override
  public String toString() {
    return (
      "Service [id=" +
      id +
      ", serviceName=" +
      serviceName +
      ", description=" +
      description +
      ", serviceImage=" +
      serviceImage +
      ", price=" +
      price +
      ", location=" +
      "]"
    );
  }
}
