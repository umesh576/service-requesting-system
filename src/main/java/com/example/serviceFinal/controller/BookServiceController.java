package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.BookService;
import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.repository.UserRepository;
import com.example.serviceFinal.service.BookServiceService;
import java.util.List;
import java.util.Optional;
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

  // Create a new booking
  @PostMapping("/book")
  public ResponseEntity<BookService> createBookService(
    @RequestBody BookService bookService
  ) {
    try {
      // 1. Validate user object
      System.out.print("this is updated");
      if (
        bookService.getUser() == null || bookService.getUser().getId() == null
      ) {
        return ResponseEntity.badRequest().build();
      }

      // 2. Fetch user from DB
      User user = userRepository
        .findById(bookService.getUser().getId())
        .orElseThrow(() -> new RuntimeException("User not found"));

      System.out.println(user);
      // 3. Set user to booking
      bookService.setUser(user);
      bookService.setStatus(BookService.Status.REQUESTED);

      // 4. Save booking
      BookService savedBooking = bookServiceService.saveBookService(
        bookService
      );

      return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    } catch (RuntimeException e) {
      // Handle "User not found" or other runtime exceptions
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception e) {
      // Handle all other exceptions
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
