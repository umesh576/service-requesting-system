package com.example.serviceFinal.controller;

import com.example.serviceFinal.dto.LoginRequest;
import com.example.serviceFinal.dto.LoginResponse;
import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.repository.UserRepository;
// import com.example.serviceFinal.repository.UserRepository;
import com.example.serviceFinal.service.UserService;
// import java.lang.classfile.ClassFile.Option;
import java.util.List;
import java.util.Optional;
import javax.management.RuntimeErrorException;
// import java.util.stream.Collectors;
// import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  // Add user Api
  @PostMapping("/add")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<?> CreateUser(@ModelAttribute User user) {
    // User savedUser = userRepository.save(user);
    // return ResponseEntity.ok(savedUser);
    // Validate required fields
    if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
      throw new RuntimeException("Email is required");
    }
    String newemail = user.getEmail();
    System.out.print(newemail);
    if (userRepository.findByEmail(newemail).isPresent()) {
      throw new RuntimeException("Email already exists");
    }
    User savedUser = userService.registerUser(user);
    return ResponseEntity.ok(savedUser);
  }

  // delete user
  @DeleteMapping("/deleteuser/{id}")
  public ResponseEntity<?> DeleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
    return ResponseEntity.ok("user is deleted sucessfully");
  }

  // update user Api
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

  // Get all user by api
  @GetMapping("/searchuser")
  public List<User> findallUser() {
    return (List<User>) userRepository.findAll();
  }

  @GetMapping("/searchuser/{id}")
  public Optional<User> getUserById(@PathVariable Long id) {
    return userRepository.findById(id);
  }

  @PostMapping("/login")
  public ResponseEntity<?> userLogin(
    @ModelAttribute LoginRequest loginRequest
  ) {
    try {
      // Find user by email
      Optional<User> userOptional = userRepository.findByEmail(
        loginRequest.getEmail()
      );
      System.out.print(userOptional);

      if (userOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
          new LoginResponse("Invalid email or password", false)
        );
      }

      User user = userOptional.get();

      // Verify password (assuming you're storing hashed passwords)
      if (
        !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())
      ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
          new LoginResponse("Invalid email or password", false)
        );
      }

      // Check if user is active/enabled

      // Successful login - create response
      LoginResponse response = new LoginResponse("Login successful", true);
      response.setUser(user); // Optionally include user details

      // For token-based authentication (JWT example):
      String token = generateToken(user);
      response.setToken(token);

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        new LoginResponse("Login failed: " + e.getMessage(), false)
      );
    }
  }

  private String generateToken(User user) {
    throw new UnsupportedOperationException(
      "Unimplemented method 'generateToken'"
    );
  }
}
