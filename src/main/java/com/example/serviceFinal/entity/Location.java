package com.example.serviceFinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(
  name = "location",
  uniqueConstraints = { @UniqueConstraint(columnNames = "ServiceAvaliable") }
)
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "ServiceAvaliable", unique = true)
  @NotBlank(message = "Location name is required")
  @NotNull(message = "Location cannot be null")
  private String location;

  // One-to-Many relationship with Service entity
  @OneToMany(
    mappedBy = "location",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @JsonIgnore // To prevent infinite recursion in JSON
  private List<Service> services;

  // Add more fields if needed (like coordinates)
  // @Column(name = "latitude")
  // private Double latitude;

  // @Column(name = "longitude")
  // private Double longitude;
  public Location() {}

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return "Location [id=" + id + ", location=" + location + "]";
  }
}
