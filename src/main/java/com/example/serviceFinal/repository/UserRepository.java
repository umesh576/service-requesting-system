package com.example.serviceFinal.repository;

import com.example.serviceFinal.entity.User;
// import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  // Method must follow naming convention
  Optional<User> findByEmail(String email);
  Optional<User> findById(int id);
}
