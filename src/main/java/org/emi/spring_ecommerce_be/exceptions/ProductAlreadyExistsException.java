package org.emi.spring_ecommerce_be.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {

  public ProductAlreadyExistsException(String message) {
    super(message);
  }
}
