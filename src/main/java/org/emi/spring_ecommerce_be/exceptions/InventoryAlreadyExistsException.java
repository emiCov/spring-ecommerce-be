package org.emi.spring_ecommerce_be.exceptions;

public class InventoryAlreadyExistsException extends RuntimeException {

  public InventoryAlreadyExistsException(String message) {
    super(message);
  }
}
