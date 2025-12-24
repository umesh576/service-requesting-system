package com.example.serviceFinal.repository;

import com.example.serviceFinal.entity.Worker;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
  Optional<Worker> findByworkerEmail(String email);
}
