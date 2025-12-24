package com.example.serviceFinal.service;

import com.example.serviceFinal.entity.Worker;
import com.example.serviceFinal.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerService {

  @Autowired
  private WorkerRepository workerRepository;

  public Worker saveWorker(Worker worker) {
    return workerRepository.save(worker);
  }
}
