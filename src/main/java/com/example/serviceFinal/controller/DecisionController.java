package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.DecisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookServiceDecion")
public class DecisionController {

  @PostMapping("/accepted/{userId}/{bookedServiceId}/{workerId}")
  public ResponseEntity<?> acccptedService(
    @PathVariable int userId,
    @PathVariable int bookedServiceId,
    @PathVariable int workerId
  ) {
    return ResponseEntity.ok("Service has accepted sucessfully.");
  }
}
