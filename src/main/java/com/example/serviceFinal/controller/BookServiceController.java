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
@RequestMapping("/book-services")
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
    // 1. Validate user object
    if (
      bookService.getUser() == null || bookService.getUser().getId() == null
    ) {
      return ResponseEntity.badRequest().build();
    }

    // 2. Fetch user from DB
    User user = userRepository
      .findById(bookService.getUser().getId())
      .orElseThrow(() -> new RuntimeException("User not found"));

    // 3. Set user to booking
    bookService.setUser(user);
    bookService.setStatus(BookService.Status.REQUESTED);

    // 4. Save booking
    BookService savedBooking = bookServiceService.saveBookService(bookService);

    // 5. Optional (for in-memory sync)
    user.getBookings().add(savedBooking);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
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
