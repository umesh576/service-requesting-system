package com.example.serviceFinal.dto;

import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.entity.Worker;

public class LoginResponse {

  private String message;
  private boolean success;
  private String token;
  private UserDTO user; // Change from User to UserDTO
  private Worker worker;

  public Worker getWorker() {
    return worker;
  }

  public void setWorker(Worker worker) {
    this.worker = worker;
  }

  // Constructor
  public LoginResponse(String message, boolean success) {
    this.message = message;
    this.success = success;
  }

  // Getters and setters
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = new UserDTO(user); // Convert User to UserDTO
  }

  // Alternative setter
  public void setUserDTO(UserDTO userDTO) {
    this.user = userDTO;
  }
}
