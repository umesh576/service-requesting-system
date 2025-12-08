package com.example.serviceFinal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "User")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "Name")
  private String name;

  @Column(name = "number")
  private long number;

  @Column(name = "email")
  private long email;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;

  @Column(name = "location")
  private String location;

  @Column(name = "dob")
  private String dob;

  @Column(name = "otp")
  private String otp;

  public User() {}

  public User(
    String name,
    long number,
    long email,
    String password,
    String role,
    String location,
    String dob,
    String otp
  ) {
    this.name = name;
    this.number = number;
    this.email = email;
    this.password = password;
    this.role = role;
    this.location = location;
    this.dob = dob;
    this.otp = otp;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getNumber() {
    return number;
  }

  public void setNumber(long number) {
    this.number = number;
  }

  public long getEmail() {
    return email;
  }

  public void setEmail(long email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

  @Override
  public String toString() {
    return (
      "User [name=" +
      name +
      ", number=" +
      number +
      ", email=" +
      email +
      ", password=" +
      password +
      ", role=" +
      role +
      ", location=" +
      location +
      ", dob=" +
      dob +
      ", otp=" +
      otp +
      ", getId()=" +
      getId() +
      ", getName()=" +
      getName() +
      ", getNumber()=" +
      getNumber() +
      ", getClass()=" +
      getClass() +
      ", getEmail()=" +
      getEmail() +
      ", getPassword()=" +
      getPassword() +
      ", getRole()=" +
      getRole() +
      ", getLocation()=" +
      getLocation() +
      ", getDob()=" +
      getDob() +
      ", getOtp()=" +
      getOtp() +
      ", hashCode()=" +
      hashCode() +
      ", toString()=" +
      super.toString() +
      "]"
    );
  }
}
