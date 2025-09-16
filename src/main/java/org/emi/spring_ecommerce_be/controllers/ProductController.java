package org.emi.spring_ecommerce_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import org.emi.spring_ecommerce_be.dtos.ProductRequestDto;
import org.emi.spring_ecommerce_be.dtos.ProductResponseDto;
import org.emi.spring_ecommerce_be.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

  private final ProductService productService;

  private static final String PRODUCTS_PER_PAGE = "5";

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  @Operation(
      summary = "Add a new product",
      description = "Creates a new product along with optional technical details")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Product created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(
            responseCode = "409",
            description = "Product with same code already exists",
            content = @Content)
      })
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponseDto addProduct(@Valid @RequestBody ProductRequestDto productRequest) {
    return productService.addProduct(productRequest);
  }

  @PutMapping
  @Operation(description = "Update a product")
  @ResponseStatus(HttpStatus.OK)
  public ProductResponseDto updateProduct(@Valid @RequestBody ProductRequestDto productRequest) {
    return productService.updateProduct(productRequest);
  }

  @DeleteMapping("/{productCode}")
  @Operation(description = "Delete product by code")
  @ResponseStatus(HttpStatus.OK)
  public void deleteProduct(@PathVariable String productCode) {
    productService.deleteProductByCode(productCode);
  }

  @GetMapping()
  @Operation(description = "Get all products")
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponseDto> getProducts() {
    return productService.getProducts();
  }

  @GetMapping("/page/{pageNo}")
  @Operation(description = "Get all products paginated")
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponseDto> getProductsByPage(
      @PathVariable int pageNo,
      @RequestParam(defaultValue = PRODUCTS_PER_PAGE) int pageSize,
      @RequestParam(defaultValue = "name") String sortField,
      @RequestParam(defaultValue = "asc") String sortDir) {
    return productService.getProductsByPage(pageNo, pageSize, sortField, sortDir);
  }

  @GetMapping("/{productCode}")
  @Operation(description = "Get product by code")
  @ResponseStatus(HttpStatus.OK)
  public ProductResponseDto getProduct(@PathVariable String productCode) {
    return productService.getProductByCode(productCode);
  }
}
