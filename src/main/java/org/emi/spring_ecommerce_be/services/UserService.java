package org.emi.spring_ecommerce_be.services;

import org.emi.spring_ecommerce_be.db.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
