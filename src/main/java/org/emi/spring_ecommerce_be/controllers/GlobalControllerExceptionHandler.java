package org.emi.spring_ecommerce_be.controllers;

import org.emi.spring_ecommerce_be.dtos.ErrorDto;
import org.emi.spring_ecommerce_be.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({
    ProductNotFoundException.class,
    InventoryNotFoundException.class,
    UserNotFoundException.class,
    OrderNotFoundException.class
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

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException ex) {
    var status = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(new ErrorDto(status.value(), ex.getMessage()), status);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
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
