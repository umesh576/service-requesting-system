package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.BookService;
import com.example.serviceFinal.entity.Service;
import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.repository.ServiceRepository;
import com.example.serviceFinal.repository.UserRepository;
import com.example.serviceFinal.service.BookServiceService;
import java.util.List;
import java.util.Optional;
import javax.management.RuntimeErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bookservices")
public class BookServiceController {

  @Autowired
  private BookServiceService bookServiceService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ServiceRepository serviceRepository;

  @PostMapping("/book")
  public ResponseEntity<?> bookService(@RequestBody BookService bookService) {
    try {
      // 1. Validate user ID
      if (bookService.getUserId() <= 0) {
        return ResponseEntity.badRequest().body("User Id is required.");
      }

      // 2. Find user
      Optional<User> userOptional = userRepository.findById(
        bookService.getUserId()
      );

      if (userOptional.isEmpty()) {
        return ResponseEntity.badRequest().body("User not found.");
      }

      User user = userOptional.get();

      // 3. Validate service ID
      if (bookService.getServiceId() <= 0) {
        return ResponseEntity.badRequest().body("Service Id is required.");
      }

      // 4. Find service
      Optional<Service> serviceOptional = serviceRepository.findById(
        bookService.getServiceId()
      );

      if (serviceOptional.isEmpty()) {
        return ResponseEntity.badRequest().body("Service not found");
      }

      // 5. Set default status if not provided
      if (bookService.getStatus() == null) {
        bookService.setStatus(BookService.Status.REQUESTED);
      }

      // 6. Save the booking
      BookService savedBooking = bookServiceService.saveBookService(
        bookService
      );

      // 7. Update user with the booked service ID
      user.setBookServiceId(savedBooking.getId()); // CORRECT: Use getBookServiceId()
      userRepository.save(user); // Save the updated user

      return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        "Error booking service"
      );
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<BookService>> getAllBookedService() {
    List<BookService> allBookedService =
      bookServiceService.getAllBookServices();
    return ResponseEntity.ok(allBookedService);
  }

  @GetMapping("/{id}")
  public Optional<BookService> getBookServiceById(@PathVariable int id) {
    Optional<BookService> bookservice = bookServiceService.getBookServiceById(
      id
    );
    return bookservice;
  }

  @DeleteMapping("/delete/{id}")
  public String deleteBookedService(@PathVariable int id) {
    bookServiceService.deleteBookService(id);
    return "Booked Service can deleted sucessfully";
  }
}
