package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.repository.UserRepository;
import com.example.serviceFinal.service.JwtService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

  //   @Autowired
  //   private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtservice;

  @Autowired
  private UserRepository userRepository;

  // Token validation endpoint - accept token as parameter
  @GetMapping("/validate")
  public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
    try {
      // Check if token is empty
      if (token == null || token.trim().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          Map.of(
            "success",
            false,
            "message",
            "Token is required",
            "valid",
            false
          )
        );
      }

      // Check if token is expired
      if (jwtservice.isTokenExpired(token)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
          Map.of(
            "success",
            false,
            "message",
            "Token has expired. Please login again",
            "valid",
            false,
            "expired",
            true
          )
        );
      }

      // Check if token is valid
      if (!jwtservice.isTokenValid(token)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
          Map.of("success", false, "message", "Invalid token", "valid", false)
        );
      }

      // Extract user information from token
      String email = jwtservice.extractUsername(token);
      String role = jwtservice.extractRole(token);

      // Fetch user details from database
      Optional<User> userOptional = userRepository.findByEmail(email);

      if (userOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
          Map.of("success", false, "message", "User not found", "valid", false)
        );
      }

      User user = userOptional.get();

      // Create response with user details
      Map<String, Object> response = new HashMap<>();
      response.put("success", true);
      response.put("message", "Token is valid");
      response.put("valid", true);
      response.put(
        "user",
        Map.of(
          "id",
          user.getId(),
          "email",
          user.getEmail(),
          "name",
          user.getName(), // assuming you have name field
          "role",
          user.getRole().name()
        )
      );
      response.put(
        "tokenInfo",
        Map.of(
          "email",
          email,
          "role",
          role,
          "expiresAt",
          jwtservice.extractExpiration(token)
        )
      );

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        Map.of(
          "success",
          false,
          "message",
          "Invalid token format",
          "valid",
          false
        )
      );
    }
  }

  // Alternative: Token validation with Authorization header
  @GetMapping("/validate-header")
  public ResponseEntity<?> validateTokenHeader(
    @RequestHeader(value = "Authorization", required = false) String authHeader
  ) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        Map.of(
          "success",
          false,
          "message",
          "Authorization header is missing or invalid",
          "valid",
          false
        )
      );
    }

    String token = authHeader.substring(7);
    return validateToken(token);
  }
}
