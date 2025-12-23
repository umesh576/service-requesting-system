package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.BookService;
import com.example.serviceFinal.service.BookServiceService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-services")
@CrossOrigin(origins = "*")
public class BookServiceController {

  @Autowired
  private BookServiceService bookServiceService;

  // Create a new booking
  @PostMapping("/book")
  public ResponseEntity<BookService> createBookService(
    @RequestBody BookService bookService
  ) {
    try {
      // Set default status to REQUESTED if not provided
      if (bookService.getStatus() == null) {
        bookService.setStatus(BookService.Status.REQUESTED);
      }

      // Validate required fields
      if (bookService.getUserId() <= 0) {
        return ResponseEntity.badRequest().body(null);
      }

      if (bookService.getServiceId() <= 0) {
        return ResponseEntity.badRequest().body(null);
      }

      if (
        bookService.getTime() == null || bookService.getTime().trim().isEmpty()
      ) {
        return ResponseEntity.badRequest().body(null);
      }

      BookService savedBooking = bookServiceService.saveBookService(
        bookService
      );
      return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    } catch (Exception e) {
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
