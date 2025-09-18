package org.emi.spring_ecommerce_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.emi.spring_ecommerce_be.dtos.AddToCartRequestDto;
import org.emi.spring_ecommerce_be.dtos.CartItemResponseDto;
import org.emi.spring_ecommerce_be.services.CartItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
public class CartItemController {

  @Value("${app.dev.default-user-email}")
  private String devDefaultUser;

  private final CartItemService cartItemService;

  public CartItemController(CartItemService cartItemService) {
    this.cartItemService = cartItemService;
  }

  @PostMapping("/items")
  @Operation(
      summary = "Add a product to the user's shopping cart",
      description =
          "Add one or more (quantity) products with a certain code to the user's shopping cart")
  @ResponseStatus(HttpStatus.CREATED)
  public String addProductToCart(
      @Valid @RequestBody AddToCartRequestDto request,
      @RequestHeader(name = "X-User-Email", required = false) String emailFromHeader) {
    String userEmail = resolveUserEmail(emailFromHeader);

    return cartItemService.addProductToCart(userEmail, request);
  }

  @DeleteMapping("/items/{productCode}")
  @Operation(description = "Delete a product from the user's shopping cart")
  @ResponseStatus(HttpStatus.OK)
  public String deleteProductFromCart(
      @PathVariable String productCode,
      @RequestHeader(name = "X-User-Email", required = false) String emailFromHeader) {
    String userEmail = resolveUserEmail(emailFromHeader);

    return cartItemService.deleteProductFromCart(userEmail, productCode);
  }

  @GetMapping("/user")
  @Operation(description = "Get the shopping cart for a user")
  @ResponseStatus(HttpStatus.OK)
  public List<CartItemResponseDto> getCartForUser(
      @RequestHeader(name = "X-User-Email", required = false) String emailFromHeader) {
    String userEmail = resolveUserEmail(emailFromHeader);

    return cartItemService.getCartForUser(userEmail);
  }

  @DeleteMapping("/user")
  @Operation(description = "Empty the shopping cart for a user")
  @ResponseStatus(HttpStatus.OK)
  public String deleteCartForUser(
      @RequestHeader(name = "X-User-Email", required = false) String emailFromHeader) {
    String userEmail = resolveUserEmail(emailFromHeader);

    return cartItemService.deleteCartForUser(userEmail);
  }

  private String resolveUserEmail(String emailFromHeader) {
    return emailFromHeader != null ? emailFromHeader : devDefaultUser;
  }
}
