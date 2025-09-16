package org.emi.spring_ecommerce_be.services;

import java.util.List;
import org.emi.spring_ecommerce_be.db.entities.InventoryEntity;
import org.emi.spring_ecommerce_be.db.repositories.InventoryRepository;
import org.emi.spring_ecommerce_be.db.repositories.ProductRepository;
import org.emi.spring_ecommerce_be.dtos.InventoryRequestDto;
import org.emi.spring_ecommerce_be.dtos.InventoryResponseDto;
import org.emi.spring_ecommerce_be.exceptions.InventoryNotFoundException;
import org.emi.spring_ecommerce_be.exceptions.ProductAlreadyExistsException;
import org.emi.spring_ecommerce_be.exceptions.ProductNotFoundException;
import org.emi.spring_ecommerce_be.mappers.InventoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InventoryService {

  private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

  private final InventoryRepository inventoryRepository;
  private final InventoryMapper inventoryMapper;
  private final ProductRepository productRepository;

  public InventoryService(
      InventoryRepository inventoryRepository,
      InventoryMapper inventoryMapper,
      ProductRepository productRepository) {
    this.inventoryRepository = inventoryRepository;
    this.inventoryMapper = inventoryMapper;
    this.productRepository = productRepository;
  }

  @Transactional(readOnly = false)
  public InventoryResponseDto addInventory(InventoryRequestDto request) {
    validateRequest(request);
    return inventoryMapper.toInventoryResponse(
        inventoryRepository.save(inventoryMapper.toInventoryEntity(request)));
  }

  @Transactional(readOnly = false)
  public InventoryResponseDto updateInventory(InventoryRequestDto request) {
    validateProductExists(request.code());

    InventoryEntity existingEntity = findInventoryByCode(request.code());
    inventoryMapper.updateEntityFromDto(request, existingEntity);

    return inventoryMapper.toInventoryResponse(inventoryRepository.save(existingEntity));
  }

  @Transactional(readOnly = false)
  public void deleteInventoryByCode(String inventoryCode) {
    inventoryRepository.delete(findInventoryByCode(inventoryCode));
  }

  public List<InventoryResponseDto> getInventories() {
    return inventoryMapper.toInventoryResponseList(inventoryRepository.findAll());
  }

  public InventoryResponseDto getInventoryByCode(String code) {
    return inventoryMapper.toInventoryResponse(findInventoryByCode(code));
  }

  private void validateRequest(InventoryRequestDto inventoryRequestDto) {
    String code = inventoryRequestDto.code();
    if (inventoryRepository.existsByCode(code)) {
      log.error("Inventory with code: {} already exist. Inventory not created.", code);
      throw new ProductAlreadyExistsException(
          String.format("Inventory with code: %s already exists", code));
    }
    validateProductExists(code);
  }

  private void validateProductExists(String code) {
    if (!productRepository.existsByCode(code)) {
      log.error("Product with code: {} does not exist. Inventory not created.", code);
      throw new ProductNotFoundException(
          String.format("Product with code: %s does not exist", code));
    }
  }

  private InventoryEntity findInventoryByCode(String code) {
    return inventoryRepository
        .findByCode(code)
        .orElseThrow(
            () -> {
              log.error("Inventory with code: {} not found", code);
              return new InventoryNotFoundException(
                  String.format("Inventory with code: %s not found", code));
            });
  }
}
