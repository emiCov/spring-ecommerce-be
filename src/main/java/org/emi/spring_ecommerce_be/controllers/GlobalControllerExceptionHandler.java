package org.emi.spring_ecommerce_be.controllers;

import org.emi.spring_ecommerce_be.dtos.ErrorDto;
import org.emi.spring_ecommerce_be.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  @ExceptionHandler({
    ProductNotFoundException.class,
    InventoryNotFoundException.class,
    UserNotFoundException.class
  })
  public ResponseEntity<ErrorDto> handleNotFoundException(RuntimeException ex) {
    var status = HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(new ErrorDto(status.value(), ex.getMessage()), status);
  }

  @ExceptionHandler({
    ProductAlreadyExistsException.class,
    InventoryAlreadyExistsException.class,
    UserAlreadyExistsException.class
  })
  public ResponseEntity<ErrorDto> handleAlreadyExistsException(RuntimeException ex) {
    var status = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(new ErrorDto(status.value(), ex.getMessage()), status);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
    StringBuilder errorMessage = new StringBuilder();
    ex.getBindingResult().getFieldErrors().forEach(error -> appendError(errorMessage, error));
    ex.getBindingResult().getGlobalErrors().forEach(error -> appendError(errorMessage, error));
    return new ResponseEntity<>(
        new ErrorDto(
            HttpStatus.BAD_REQUEST.value(),
            errorMessage.substring(0, errorMessage.lastIndexOf(", "))),
        HttpStatus.BAD_REQUEST);
  }

  private void appendError(StringBuilder errorMessage, ObjectError error) {
    if (error instanceof FieldError) {
      errorMessage.append(((FieldError) error).getField());
    } else {
      errorMessage.append(error.getCode());
    }
    errorMessage.append(": ");
    errorMessage.append(error.getDefaultMessage());
    errorMessage.append(", ");
  }
}
