package com.example.serviceFinal.service;

import com.example.serviceFinal.entity.BookService;
import com.example.serviceFinal.repository.BookServiceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceService {

  @Autowired
  private BookServiceRepository bookServiceRepository;

  public BookService saveBookService(BookService bookService) {
    return bookServiceRepository.save(bookService);
  }

  public List<BookService> getAllBookServices() {
    return bookServiceRepository.findAll();
  }

  public Optional<BookService> getBookServiceById(int id) {
    return bookServiceRepository.findById(id);
  }

  public void deleteBookService(int id) {
    bookServiceRepository.deleteById(id);
  }

  // Update this method name to match repository
  // public List<BookService> getBookServicesByUserId(int userId) {
  //   return bookServiceRepository.findByUser_Id(userId); // Changed to findByUser_Id
  // }

  public List<BookService> getBookServicesByServiceId(int serviceId) {
    return bookServiceRepository.findByServiceId(serviceId);
  }

  public List<BookService> getBookServicesByStatus(BookService.Status status) {
    return bookServiceRepository.findByStatus(status);
  }
}
