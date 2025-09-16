package org.emi.spring_ecommerce_be.exceptions;

public class InventoryNotFoundException extends RuntimeException {

  public InventoryNotFoundException(String message) {
    super(message);
  }
}
