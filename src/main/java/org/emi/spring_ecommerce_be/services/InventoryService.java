package org.emi.spring_ecommerce_be.services;

import org.emi.spring_ecommerce_be.db.repositories.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

  private final InventoryRepository inventoryRepository;

  public InventoryService(InventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }
}
