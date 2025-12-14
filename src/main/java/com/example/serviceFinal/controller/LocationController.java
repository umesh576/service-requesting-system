package com.example.serviceFinal.controller;

import com.example.serviceFinal.entity.Location;
import com.example.serviceFinal.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
public class LocationController {

  @Autowired
  private LocationRepository locationrepository;

  @PostMapping("/add")
  public ResponseEntity<?> CreateLocation(@RequestBody Location location) {
    Location ln = locationrepository.save(location);

    return ResponseEntity.ok(ln);
  }
}
