package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.BookService;
import com.example.serviceFinal.entity.User;
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
