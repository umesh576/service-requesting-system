package com.example.serviceFinal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookedService")
public class BookService {

  public enum Status {
    SUCESS, // Typo: Should be "SUCCESS"
    REQUESTED,
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  // @ManyToOne
  // @JoinColumn(name = "user_id", nullable = false)
  // private User user;

  // @Column(name = "user_Id")
  // private int user;

  @Column(name = "serviceId", nullable = false)
  private int serviceId;

  @Column(name = "avaliable_time", nullable = false) // Typo: "available_time"
  private String time;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status = Status.REQUESTED;

  @Column(name = "message")
  private String message;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getServiceId() {
    return serviceId;
  }

  public void setServiceId(int serviceId) {
    this.serviceId = serviceId;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  // public int getUser() {
  //   return user;
  // }

  // public void setUser(User user2) {
  //   this.user = user;
  // }
}
