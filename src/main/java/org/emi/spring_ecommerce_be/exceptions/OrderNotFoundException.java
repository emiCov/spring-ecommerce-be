package org.emi.spring_ecommerce_be.exceptions;

public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException(String message) {
    super(message);
  }
}
