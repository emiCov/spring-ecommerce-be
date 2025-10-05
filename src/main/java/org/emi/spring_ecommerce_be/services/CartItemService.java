package org.emi.spring_ecommerce_be.services;

import java.util.List;
import org.emi.spring_ecommerce_be.db.entities.CartItemEntity;
import org.emi.spring_ecommerce_be.db.entities.ProductEntity;
import org.emi.spring_ecommerce_be.db.entities.UserEntity;
import org.emi.spring_ecommerce_be.db.repositories.CartItemRepository;
import org.emi.spring_ecommerce_be.db.repositories.ProductRepository;
import org.emi.spring_ecommerce_be.dtos.AddToCartRequestDto;
import org.emi.spring_ecommerce_be.dtos.CartItemResponseDto;
import org.emi.spring_ecommerce_be.dtos.InventoryResponseDto;
import org.emi.spring_ecommerce_be.exceptions.ProductNotFoundException;
import org.emi.spring_ecommerce_be.mappers.CartItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartItemService {

  private static final Logger log = LoggerFactory.getLogger(CartItemService.class);

  private static final String PRODUCT_NOT_FOUND_MSG = "Product with code: %s was not found";
  private static final String NOT_ENOUGH_PRODUCTS_MSG = "Not enough products in stock for: %s.";
  private static final String PRODUCT_REMOVED_MSG =
      "The product has been removed from the shopping cart.";
  private static final String PRODUCT_NOT_IN_CART_MSG = "The product is not in the cart";

  private final CartItemRepository cartItemRepository;
  private final ProductRepository productRepository;
  private final InventoryService inventoryService;
  private final CartItemMapper cartItemMapper;
  private final AuthenticationService authenticationService;

  public CartItemService(
      CartItemRepository cartItemRepository,
      ProductRepository productRepository,
      InventoryService inventoryService,
      CartItemMapper cartItemMapper,
      AuthenticationService authenticationService) {
    this.cartItemRepository = cartItemRepository;
    this.productRepository = productRepository;
    this.inventoryService = inventoryService;
    this.cartItemMapper = cartItemMapper;
    this.authenticationService = authenticationService;
  }

  @Transactional(readOnly = false)
  public String addProductToCart(AddToCartRequestDto request) {
    validateRequest(request);

    UserEntity user = authenticationService.getAuthenticatedUser();

    ProductEntity product = findProductByCode(request.productCode());

    CartItemEntity cartItem =
        cartItemRepository
            .findByUser_EmailAndProduct_Code(user.getEmail(), request.productCode())
            .orElseGet(CartItemEntity::new);

    if (null == cartItem.getProduct()) {
      cartItem.setUser(user);
      cartItem.setProduct(product);
      cartItem.setQuantity(request.quantity());
    } else {
      cartItem.setQuantity((short) (cartItem.getQuantity() + request.quantity()));
    }

    cartItemRepository.save(cartItem);

    return "Product " + request.productCode() + " added successfully.";
  }

  @Transactional(readOnly = false)
  public String deleteProductFromCart(String productCode) {
    UserEntity user = authenticationService.getAuthenticatedUser();
    long removedProducts =
        cartItemRepository.deleteByUser_EmailAndProduct_Code(user.getEmail(), productCode);

    return removedProducts > 0 ? PRODUCT_REMOVED_MSG : PRODUCT_NOT_IN_CART_MSG;
  }

  public List<CartItemResponseDto> getCartForUser() {
    UserEntity user = authenticationService.getAuthenticatedUser();

    return cartItemRepository.findByUser_Email(user.getEmail()).stream()
        .map(cartItemMapper::toCartItemResponseDto)
        .toList();
  }

  @Transactional(readOnly = false)
  public String deleteCartForUser() {
    UserEntity user = authenticationService.getAuthenticatedUser();
    List<CartItemEntity> cartItems = cartItemRepository.findByUser_Email(user.getEmail());

    if (cartItems.isEmpty()) {
      return "The cart is already empty";
    }
    cartItemRepository.deleteAll(cartItems);

    return "The cart is now empty";
  }

  public List<CartItemEntity> getCartEntityForUser(String userEmail) {
    return cartItemRepository.findByUser_Email(userEmail);
  }

  private void validateRequest(AddToCartRequestDto request) {
    InventoryResponseDto inventory = inventoryService.getInventoryByCode(request.productCode());
    if (inventory.quantity() < request.quantity()) {
      String message = String.format(NOT_ENOUGH_PRODUCTS_MSG, request.productCode());
      log.error(message);
      throw new IllegalArgumentException(message);
    }
  }

  private ProductEntity findProductByCode(String productCode) {
    return productRepository
        .findByCode(productCode)
        .orElseThrow(
            () -> {
              String message = String.format(PRODUCT_NOT_FOUND_MSG, productCode);
              log.error(message);
              return new ProductNotFoundException(message);
            });
  }
}
