package com.example.serviceFinal.entity;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
// import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

// import org.springframework.web.multipart.MultipartFile;

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
  private Integer id;

  @Column(nullable = false)
  private String serviceName;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private Double price;

  @Column(name = "service_image")
  private String serviceImage; // ✅ URL ONLY

  @ManyToOne
  @JoinColumn(name = "location_id", nullable = false)
  private Location location;

  // Constructors
  public Service() {}

  public Service(
    String serviceName,
    String description,
    Double price,
    Location location
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

  public void setServiceImage(String imageUrl) {
    this.serviceImage = imageUrl;
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
