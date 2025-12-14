package com.example.serviceFinal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
}
