package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.repository.UserRepository;
// import com.example.serviceFinal.repository.UserRepository;
import com.example.serviceFinal.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @Autowired
  private UserService userService;

  @PostMapping("/add")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<?> CreateStudent(@RequestBody User user) {
    // User savedUser = userRepository.save(user);
    // return ResponseEntity.ok(savedUser);
    User savedUser = userService.registerUser(user);
    return ResponseEntity.ok(savedUser);
  }

  @DeleteMapping("/deleteuser/{id}")
  public ResponseEntity<?> DeleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
    return ResponseEntity.ok("user is deleted sucessfully");
  }

  @PatchMapping("/updateuser/{id}")
  public ResponseEntity<?> updateUser(
    @PathVariable Long id,
    @RequestBody User updatedUser
  ) {
    Optional<User> userOptional = userRepository.findById(id);

    if (userOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    User savedUser = userOptional.get();

    if (updatedUser.getName() != null) {
      savedUser.setName(updatedUser.getName());
    }
    if (updatedUser.getEmail() != null) {
      savedUser.setEmail(updatedUser.getEmail());
    }
    if (updatedUser.getLocation() != null) {
      savedUser.setLocation(updatedUser.getLocation());
    }
    if (updatedUser.getNumber() != 0) {
      savedUser.setNumber(updatedUser.getNumber());
    }
    if (updatedUser.getDob() != null) {
      savedUser.setDob(updatedUser.getDob());
    }

    userRepository.save(savedUser);
    return ResponseEntity.ok(savedUser);
  }
  // @PostMapping("/login")
  // public ResponseEntity<?> loginUser(@RequestBody  entity) {
  //   //TODO: process POST request

  //   return entity;
  // }
}
