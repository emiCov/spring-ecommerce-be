package org.emi.spring_ecommerce_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.emi.spring_ecommerce_be.dtos.InventoryRequestDto;
import org.emi.spring_ecommerce_be.dtos.InventoryResponseDto;
import org.emi.spring_ecommerce_be.services.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {

  private final InventoryService inventoryService;

  public InventoryController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @PostMapping
  @Operation(description = "Add inventory")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  public InventoryResponseDto addInventory(@Valid @RequestBody InventoryRequestDto request) {
    return inventoryService.addInventory(request);
  }

  @PutMapping
  @Operation(description = "Update inventory")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ADMIN')")
  public InventoryResponseDto updateInventory(@Valid @RequestBody InventoryRequestDto request) {
    return inventoryService.updateInventory(request);
  }

  @DeleteMapping("/{inventoryCode}")
  @Operation(description = "Delete inventory")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteInventory(@PathVariable String inventoryCode) {
    inventoryService.deleteInventoryByCode(inventoryCode);
  }

  @GetMapping
  @Operation(description = "Get all inventories")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ADMIN')")
  public List<InventoryResponseDto> getInventories() {
    return inventoryService.getInventories();
  }

  @GetMapping("/{code}")
  @Operation(description = "Get inventory by code")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('USER')")
  public InventoryResponseDto getInventoryByCode(@PathVariable String code) {
    return inventoryService.getInventoryByCode(code);
  }
}
