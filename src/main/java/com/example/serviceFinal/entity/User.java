package com.example.serviceFinal.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

// import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(
  name = "users",
  uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  @NotBlank(message = "Name is required")
  @Size(
    min = 3,
    max = 30,
    message = "Name has more than three letter and less than 30 letter."
  )
  private String name;

  @Column(name = "number")
  @NotNull(message = "Phone number is required")
  @Positive(message = "Number must be positive")
  @Digits(
    integer = 10,
    fraction = 0,
    message = "Phone number must be 10 digits."
  )
  private long number;

  @Column(name = "email", unique = true, nullable = false)
  @NotBlank(message = "Email is required")
  @Email(message = "Email should be vaild.")
  @Pattern(regexp = ".+@.+\\..+", message = "Email must be valid format")
  private String email;

  @Column(name = "password")
  @NotBlank(message = "Password is required")
  @Size(
    min = 6,
    max = 100,
    message = "Password must be between 6 and 20 characters"
  )
  private String password;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name = "location")
  @Size(max = 50, message = "Location cannot exceed 50 characters")
  private String location;

  @Column(name = "dob")
  @Pattern(
    regexp = "^\\d{4}/\\d{2}/\\d{2}$",
    message = "Date must be in yyyy/MM/dd format"
  )
  private String dob;

  @Column(name = "otp")
  @Size(min = 6, max = 6, message = "OTP must be exactly 6 digits")
  @Pattern(regexp = "\\d{6}", message = "OTP must contain only digits")
  private String otp;

  @Column(name = "bookedService_id", nullable = true)
  private int bookServiceId;

  public int getBookServiceId() {
    return bookServiceId;
  }

  public void setBookServiceId(int bookServiceId) {
    this.bookServiceId = bookServiceId;
  }

  public User() {}

  public User(
    String name,
    long number,
    String email,
    Role role,
    String location,
    String dob,
    String otp
  ) {
    this.name = name;
    this.number = number;
    this.email = email;
    this.role = role;
    this.location = location;
    this.dob = dob;
    this.otp = otp;
  }

  public Integer getId() {
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
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

  public enum Role {
    ADMIN,
    STAFF,
    USER,
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
