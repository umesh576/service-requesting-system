package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.BookService;
import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.repository.BookServiceRepository;
import com.example.serviceFinal.repository.UserRepository;
import com.example.serviceFinal.repository.WorkerRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
// import com.example.serviceFinal.entity.DecisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookServiceDecion")
public class DecisionController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BookServiceRepository bookServiceRepository;

  @Autowired
  private WorkerRepository workerRepository;

  @PostMapping("/accepted/{userId}/{bookedServiceId}/{workerId}")
  public ResponseEntity<?> acccptedService(
    @PathVariable Integer userId,
    @PathVariable Integer bookedServiceId,
    @PathVariable Integer workerId
  ) {
    if (userId == null || bookedServiceId == null || workerId == null) {
      return ResponseEntity.badRequest()
        .body("Please provide UserId bookedServiceId workerId");
    }

    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      return ResponseEntity.badRequest().body("User not found.");
    }

    User user = optionalUser.get();

    Optional<BookService> bookService = bookServiceRepository.findById(
      bookedServiceId
    );

    if (!bookService.isPresent()) {
      return ResponseEntity.badRequest().body("Service not found.");
    }
    BookService booking = bookService.get();

    if (bookService.isEmpty()) {}

    return ResponseEntity.ok("Service has accepted sucessfully.");
  }
}
