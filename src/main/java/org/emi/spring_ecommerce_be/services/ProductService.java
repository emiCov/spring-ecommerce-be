package org.emi.spring_ecommerce_be.services;

import org.emi.spring_ecommerce_be.db.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }
}
