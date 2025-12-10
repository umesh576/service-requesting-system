package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/add")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<?> CreateStudent( @RequestBody User user) {
    try {
    User savedUser = userRepository.save(user);
    return ResponseEntity.ok(savedUser);
      } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
  }
}
