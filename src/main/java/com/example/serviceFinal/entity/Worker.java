package com.example.serviceFinal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "worker")
public class Worker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "Worker_name")
  private String workerName;

  @Column(name = "worker_email")
  private String workerEmail;

  @Column(name = "worker_number")
  private String workerNumber;

  @Column(name = "password")
  private String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name = "Description")
  private String description;

  @Column(name = "serviceProvide")
  private String serviceProvide;

  @Column(name = "assign_work")
  private Integer assignWorkId;

  public Integer getAssignWorkId() {
    return assignWorkId;
  }

  public void setAssignWorkId(Integer assignWorkId) {
    this.assignWorkId = assignWorkId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getWorkerName() {
    return workerName;
  }

  public void setWorkerName(String workerName) {
    this.workerName = workerName;
  }

  public String getWorkerEmail() {
    return workerEmail;
  }

  public void setWorkerEmail(String workerEmail) {
    this.workerEmail = workerEmail;
  }

  public String getWorkerNumber() {
    return workerNumber;
  }

  public void setWorkerNumber(String workerNumber) {
    this.workerNumber = workerNumber;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getServiceProvide() {
    return serviceProvide;
  }

  public void setServiceProvide(String serviceProvide) {
    this.serviceProvide = serviceProvide;
  }
}
