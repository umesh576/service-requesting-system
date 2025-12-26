package com.example.serviceFinal.controller;

import com.example.serviceFinal.dto.LoginRequest;
import com.example.serviceFinal.dto.LoginResponse;
import com.example.serviceFinal.entity.Worker;
import com.example.serviceFinal.repository.WorkerRepository;
import com.example.serviceFinal.service.JwtService;
import com.example.serviceFinal.service.WorkerService;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/worker")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:3001",
    "http://localhost:3002",
    "http://127.0.0.1:3000",
    "http://127.0.0.1:3001"
})public class WorkerController {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private WorkerRepository workerRepository;

  @Autowired
  private WorkerService workerService;

  @Autowired
  private JwtService jwtservice;

  @PostMapping("/add")
  public ResponseEntity<?> addWorker(@RequestBody Worker worker) {
    if (
      worker.getDescription() == null ||
      worker.getServiceProvide() == null ||
      worker.getWorkerEmail() == null ||
      worker.getWorkerName() == null ||
      worker.getWorkerNumber() == null ||
      worker.getPassword() == null
    ) {
      return ResponseEntity.badRequest()
        .body("Please provide Required details.");
    }

    Optional<Worker> alreadyWorker = workerRepository.findByworkerEmail(
      worker.getWorkerEmail()
    );
    if (alreadyWorker.isPresent()) {
      return ResponseEntity.badRequest()
        .body("Worker is already present with this mail");
    }

    // workerService.saveWorker(worker);
    workerService.registerWorker(worker);
    return ResponseEntity.ok(worker);
  }

  @PatchMapping("/update/{id}")
  public ResponseEntity<?> updateWorker(
    @PathVariable Integer id,
    @RequestBody Worker newworker
  ) {
    try {
      // Validate ID
      if (id == null || id <= 0) {
        return ResponseEntity.badRequest().body("Valid ID is required");
      }

      // Find existing worker
      Optional<Worker> alreadyWorker = workerRepository.findById(id);
      if (alreadyWorker.isEmpty()) {
        return ResponseEntity.badRequest().body("Worker not found in system."); // Added RETURN
      }

      Worker worker = alreadyWorker.get();

      // Update fields if provided
      if (newworker.getDescription() != null) {
        worker.setDescription(newworker.getDescription());
      }

      if (newworker.getServiceProvide() != null) {
        worker.setServiceProvide(newworker.getServiceProvide());
      }

      if (newworker.getWorkerEmail() != null) {
        worker.setWorkerEmail(newworker.getWorkerEmail());
      }

      if (newworker.getWorkerName() != null) {
        worker.setWorkerName(newworker.getWorkerName());
      }

      if (newworker.getWorkerNumber() != null) {
        worker.setWorkerNumber(newworker.getWorkerNumber());
      }

      // Save and return updated worker
      Worker updatedWorker = workerService.saveWorker(worker);
      return ResponseEntity.ok(updatedWorker);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(
        "Error updating worker"
      );
    }
  }

  @GetMapping("/")
  public List<Worker> getAllWorker() {
    return workerRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getWorkerById(@PathVariable Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().body("id is required");
    }
    return ResponseEntity.ok(workerRepository.findById(id));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteWorker(@PathVariable Integer id) {
    if (id == null) {
      return ResponseEntity.badRequest().body("id is required");
    }
    workerRepository.deleteById(id);
    return ResponseEntity.ok("worker deleted sucessfully");
  }

  // Worker Login with JWT (Following your User pattern)
  @PostMapping("/login")
  public ResponseEntity<?> workerLogin(@RequestBody LoginRequest loginRequest) {
    try {
      // Validate request
      if (
        loginRequest.getEmail() == null ||
        loginRequest.getEmail().trim().isEmpty() ||
        loginRequest.getPassword() == null ||
        loginRequest.getPassword().trim().isEmpty()
      ) {
        return ResponseEntity.badRequest()
          .body(new LoginResponse("Email and password are required", false));
      }

      // Find worker by email
      Optional<Worker> workerOptional = workerRepository.findByworkerEmail(
        loginRequest.getEmail()
      );

      if (workerOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body(
          new LoginResponse("Invalid email or password", false)
        );
      }

      Worker worker = workerOptional.get();

      // Verify password
      if (
        !passwordEncoder.matches(
          loginRequest.getPassword(),
          worker.getPassword()
        )
      ) {
        return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body(
          new LoginResponse("Invalid email or password", false)
        );
      }

      // Generate JWT token
      String token = jwtservice.generateToken(
        worker.getWorkerEmail(),
        worker.getRole()
      );

      // Create successful response
      LoginResponse response = new LoginResponse("Login successful", true);
      response.setToken(token);
      response.setWorker(worker); // You might need to add setWorker method

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(
        new LoginResponse("Login failed: " + e.getMessage(), false)
      );
    }
  }
}
