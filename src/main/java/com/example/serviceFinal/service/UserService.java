package com.example.serviceFinal.service;

import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public User registerUser(User user) {
    // Hash the password
    String hashedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(hashedPassword);

    // Save user
    return userRepository.save(user);
  }

  public Boolean encodePassword(User user) {
    return true;
  }
}
