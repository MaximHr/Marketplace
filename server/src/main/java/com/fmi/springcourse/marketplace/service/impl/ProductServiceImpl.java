package com.fmi.springcourse.marketplace.service.impl;

import com.fmi.springcourse.marketplace.dto.PageResponse;
import com.fmi.springcourse.marketplace.dto.product.ProductCardDto;
import com.fmi.springcourse.marketplace.dto.product.ProductDetails;
import com.fmi.springcourse.marketplace.dto.product.ProductRequest;
import com.fmi.springcourse.marketplace.entity.Product;
import com.fmi.springcourse.marketplace.exception.EntityNotFoundException;
import com.fmi.springcourse.marketplace.repository.ProductRepository;
import com.fmi.springcourse.marketplace.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
	private static final int MAX_PAGE_SIZE = 100;
	
	private final ProductRepository repository;
	
	public ProductServiceImpl(ProductRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public ProductDetails createProduct(ProductRequest req) {
		if (req == null) {
			throw new IllegalArgumentException("Product request can not be null.");
		}
		
		var product = new Product(
			req.getName(), req.getDescription(), req.getPrice(), req.getQuantity(), req.getType());
		Product savedProduct = repository.save(product);
		
		return new ProductDetails(savedProduct);
	}
	
	@Override
	public ProductDetails getProductDetailsBySlug(String slug) {
		if (slug == null) {
			throw new IllegalArgumentException("slug can not be null.");
		}
		
		var product = repository.getBySlug(slug)
			.orElseThrow(() -> new EntityNotFoundException("Product not found"));
		
		return new ProductDetails(product);
	}
	
	@Override
	public PageResponse<ProductCardDto> listProducts(Pageable pageable) {
		validatePageable(pageable);
		
		Page<Product> page = repository.findAll(pageable);
		
		List<ProductCardDto> products = page.get()
			.map(ProductCardDto::new)
			.collect(Collectors.toList());
		
		return new PageResponse<>(products, page.getTotalElements(),
			page.getTotalPages());
	}
	
	@Override
	public void deleteProduct(Long id) {
		repository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Could not find product with this id."));
		
		repository.deleteById(id);
	}
	
	private void validatePageable(Pageable pageable) {
		if (pageable.getPageSize() > MAX_PAGE_SIZE || pageable.getPageSize() <= 0) {
			throw new IllegalArgumentException("Page size is incorrect.");
		}
	}
}
