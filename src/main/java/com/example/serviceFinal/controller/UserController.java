package com.example.serviceFinal.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @PostMapping("/add")
  public String postMethodName(@RequestBody String entity) {
    //TODO: process POST request

    return entity;
  }
  // @GetMapping("/")
  // public List<User> getUser() {
  //   return "umesh joshi";
  // }
}
