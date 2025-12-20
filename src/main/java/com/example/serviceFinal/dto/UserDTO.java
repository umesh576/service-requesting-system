package com.example.serviceFinal.dto;


import com.example.serviceFinal.entity.User;

// Create this class
public class UserDTO {

  private Long id;
  private String name;
  private String email;
  private User.Role role;

  // Constructor from User entity
  public UserDTO(User user) {
    this.id = (long) user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.role = user.getRole();
  }

  // Getters and setters (must have!)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail( String email) {
    this.email = email;
  }
  public User.Role getRole() {
    return role;
  }

  public void setRole(User.Role role) {
    this.role = role;
  }

 
}
