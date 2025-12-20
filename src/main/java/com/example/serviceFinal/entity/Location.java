package com.example.serviceFinal.entity;

// import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
// import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "location_name", unique = true)
  @NotBlank(message = "Location name is required")
  @NotNull(message = "Location cannot be null")
  private String locationName;

  @Column(name = "address")
  private String address;

  @Column(name = "city")
  private String city;

  // One-to-Many relationship with Service entity
  @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
  @JsonIgnoreProperties("location")
  private List<Service> services = new ArrayList<>();

  // Constructors
  public Location() {}

  public Location(String locationName, String address, String city) {
    this.locationName = locationName;
    this.address = address;
    this.city = city;
  }

  // Helper method to add service
  // public void addService(Service service) {
  //   services.add(service);
  //   service.setLocation(this);
  // }

  // Helper method to remove service
  public void removeService(Service service) {
    services.remove(service);
    service.setLocation(null);
  }

  // Getters and Setters
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public List<Service> getServices() {
    return services;
  }

  public void setServices(List<Service> services) {
    this.services = services;
  }

  @Override
  public String toString() {
    return (
      "Location [id=" +
      id +
      ", locationName=" +
      locationName +
      ", address=" +
      address +
      ", city=" +
      city +
      "]"
    );
  }
}
