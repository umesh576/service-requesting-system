package com.example.serviceFinal.repository;

import com.example.serviceFinal.entity.BookService;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookServiceRepository
  extends JpaRepository<BookService, Integer> {
  List<BookService> findByServiceId(int serviceId);
  List<BookService> findByStatus(BookService.Status status);
  // REMOVE: List<BookService> findByUser_Id(int userId);
}
