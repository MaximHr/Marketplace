package com.fmi.springcourse.marketplace.service.impl;

import com.fmi.springcourse.marketplace.dto.PageResponse;
import com.fmi.springcourse.marketplace.dto.product.ProductCardDto;
import com.fmi.springcourse.marketplace.dto.product.ProductDetails;
import com.fmi.springcourse.marketplace.dto.product.ProductRequest;
import com.fmi.springcourse.marketplace.entity.Image;
import com.fmi.springcourse.marketplace.entity.Product;
import com.fmi.springcourse.marketplace.exception.EntityNotFoundException;
import com.fmi.springcourse.marketplace.repository.ProductRepository;
import com.fmi.springcourse.marketplace.repository.impl.S3ImageRepository;
import com.fmi.springcourse.marketplace.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
	private static final int MAX_PAGE_SIZE = 100;
	
	private final ProductRepository productRepository;
	private final S3ImageRepository imageRepository;
	
	public ProductServiceImpl(ProductRepository productRepository, S3ImageRepository imageRepository) {
		this.productRepository = productRepository;
		this.imageRepository = imageRepository;
	}
	
	@Transactional
	@Override
	public ProductDetails createProduct(ProductRequest req) {
		if (req == null) {
			throw new IllegalArgumentException("Product request can not be null.");
		}
		
		var product = new Product(req.getName(), req.getDescription(), req.getPrice(), req.getQuantity(),
			req.getType(), req.getMainImage(), getAdditionalImages(req));
		
		Product savedProduct = productRepository.save(product);
		
		return new ProductDetails(savedProduct);
	}
	
	private List<Image> getAdditionalImages(ProductRequest req) {
		if (req.getAdditionalImages() != null) {
			return req.getAdditionalImages()
				.stream()
				.map(dto -> new Image(dto.name()))
				.toList();
		}
		
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ProductDetails getProductDetailsBySlug(String slug) {
		if (slug == null) {
			throw new IllegalArgumentException("slug can not be null.");
		}
		
		var product = productRepository.getBySlug(slug)
			.orElseThrow(() -> new EntityNotFoundException("Product not found"));
		
		return new ProductDetails(product);
	}
	
	@Override
	public PageResponse<ProductCardDto> listProducts(Pageable pageable) {
		validatePageable(pageable);
		
		Page<Product> page = productRepository.findAll(pageable);
		
		List<ProductCardDto> products = page.get()
			.map(ProductCardDto::new)
			.collect(Collectors.toList());
		
		return new PageResponse<>(products, page.getTotalElements(),
			page.getTotalPages());
	}
	
	@Transactional
	@Override
	public void deleteProduct(Long id) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Could not find product with this id."));
		
		imageRepository.removeImage(product.getMainImage());
		
		product.getAdditionalImages()
			.stream()
			.map(Image::getNameInBucket)
			.forEach(imageRepository::removeImage);
		
		productRepository.deleteById(id);
	}
	
	@Override
	public ProductDetails updateProduct(Long id, ProductRequest req) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Could not find product with this id."));
		
		product.setDescription(req.getDescription());
		product.setName(req.getName());
		product.setPrice(req.getPrice());
		product.setQuantity(req.getQuantity());
		product.setMainImage(req.getMainImage());
		
		return new ProductDetails(productRepository.save(product));
	}
	
	private void validatePageable(Pageable pageable) {
		if (pageable.getPageSize() > MAX_PAGE_SIZE || pageable.getPageSize() <= 0) {
			throw new IllegalArgumentException("Page size is incorrect.");
		}
	}
}
