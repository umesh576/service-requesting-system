package com.example.serviceFinal.service;

import com.example.serviceFinal.entity.User;
import com.example.serviceFinal.entity.Worker;
import com.example.serviceFinal.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WorkerService {

  @Autowired
  private WorkerRepository workerRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public Worker registerWorker(Worker worker) {
    String hashedPassword = passwordEncoder.encode(worker.getPassword());
    worker.setPassword(hashedPassword);
    return workerRepository.save(worker);
  }

  public Worker saveWorker(Worker worker) {
    return workerRepository.save(worker);
  }

  public Boolean encodePassword(User user) {
    return true;
  }
}
