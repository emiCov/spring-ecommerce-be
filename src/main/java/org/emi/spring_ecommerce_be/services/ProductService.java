package org.emi.spring_ecommerce_be.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.emi.spring_ecommerce_be.db.entities.ProductEntity;
import org.emi.spring_ecommerce_be.db.entities.TechnicalDetailsEntity;
import org.emi.spring_ecommerce_be.db.repositories.ProductRepository;
import org.emi.spring_ecommerce_be.dtos.ProductRequestDto;
import org.emi.spring_ecommerce_be.dtos.ProductResponseDto;
import org.emi.spring_ecommerce_be.dtos.TechnicalDetailsRequestDto;
import org.emi.spring_ecommerce_be.exceptions.ProductAlreadyExistsException;
import org.emi.spring_ecommerce_be.exceptions.ProductNotFoundException;
import org.emi.spring_ecommerce_be.mappers.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

  private static final Logger log = LoggerFactory.getLogger(ProductService.class);

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
  }

  @Transactional(readOnly = false)
  public ProductResponseDto addProduct(ProductRequestDto productRequest) {
    validateRequest(productRequest);
    ProductEntity product = productMapper.toProductEntity(productRequest);
    setTechnicalDetails(productRequest.technicalDetails(), product);

    return productMapper.toProductResponse(productRepository.save(product));
  }

  @Transactional(readOnly = false)
  public ProductResponseDto updateProduct(ProductRequestDto productRequest) {
    ProductEntity existingEntity = findProductByCode(productRequest.code());
    productMapper.updateEntityFromDto(productRequest, existingEntity);

    existingEntity.getTechnicalDetails().clear();
    setTechnicalDetails(productRequest.technicalDetails(), existingEntity);

    return productMapper.toProductResponse(productRepository.save(existingEntity));
  }

  @Transactional(readOnly = false)
  public void deleteProductByCode(String productCode) {
    productRepository.delete(findProductByCode(productCode));
  }

  public List<ProductResponseDto> getProducts() {
    return productMapper.toProductResponseList(productRepository.getAllProducts());
  }

  public List<ProductResponseDto> getProductsByPage(
      int pageNo, int pageSize, String sortField, String sortDir) {
    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

    pageNo = Math.max(pageNo, 1);

    return productRepository.findAll(PageRequest.of(pageNo - 1, pageSize, sort)).stream()
        .map(productMapper::toProductResponse)
        .toList();
  }

  public ProductResponseDto getProductByCode(String productCode) {
    return productMapper.toProductResponse(findProductByCode(productCode));
  }

  private void validateRequest(ProductRequestDto productRequest) {
    String code = productRequest.code();
    if (productRepository.existsByCode(code)) {
      log.error("Product with code: {} already exist. Product not created.", code);
      throw new ProductAlreadyExistsException(
          String.format("Product with code: %s already exists", code));
    }
  }

  private ProductEntity findProductByCode(String productCode) {
    return productRepository
        .findByCode(productCode)
        .orElseThrow(
            () -> {
              log.error("Product with code: {} was not found", productCode);
              return new ProductNotFoundException(
                  String.format("Product with code: %s was not found", productCode));
            });
  }

  private void setTechnicalDetails(Set<TechnicalDetailsRequestDto> request, ProductEntity product) {
    Optional.ofNullable(request)
        .ifPresent(
            r ->
                r.forEach(
                    td -> {
                      TechnicalDetailsEntity tdEntity = productMapper.toTechnicalDetailsEntity(td);
                      tdEntity.setProduct(product);
                      product.addTechnicalDetail(tdEntity);
                    }));
  }
}
