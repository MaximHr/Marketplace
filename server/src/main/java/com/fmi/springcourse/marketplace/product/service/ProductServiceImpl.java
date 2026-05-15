package com.fmi.springcourse.marketplace.product.service;

import com.fmi.springcourse.marketplace.dto.PageResponse;
import com.fmi.springcourse.marketplace.exception.AccessDeniedException;
import com.fmi.springcourse.marketplace.image.Image;
import com.fmi.springcourse.marketplace.image.repo.DbImageRepository;
import com.fmi.springcourse.marketplace.product.ProductRepository;
import com.fmi.springcourse.marketplace.product.entity.Product;
import com.fmi.springcourse.marketplace.exception.EntityNotFoundException;
import com.fmi.springcourse.marketplace.exception.OutOfStockException;
import com.fmi.springcourse.marketplace.product.dto.ProductCardDto;
import com.fmi.springcourse.marketplace.product.dto.ProductDetails;
import com.fmi.springcourse.marketplace.product.dto.ProductRequest;
import com.fmi.springcourse.marketplace.image.repo.S3ImageRepository;
import com.fmi.springcourse.marketplace.user.entity.User;
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
	private final DbImageRepository dbImageRepository;
	
	public ProductServiceImpl(ProductRepository productRepository, S3ImageRepository imageRepository,
	                          DbImageRepository dbImageRepository) {
		this.productRepository = productRepository;
		this.imageRepository = imageRepository;
		this.dbImageRepository = dbImageRepository;
	}
	
	@Transactional
	@Override
	public ProductDetails createProduct(ProductRequest req, User user) {
		if (req == null) {
			throw new IllegalArgumentException("Product request can not be null.");
		}
		
		List<Image> images = getAdditionalImages(req);
		var product = new Product(req.getName(), req.getDescription(), req.getPrice(), req.getQuantity(),
			req.getType(), req.getMainImage(), images, user);
		
		for (var img : images) {
			img.setProduct(product);
		}
		
		Product savedProduct = productRepository.save(product);
		dbImageRepository.saveAll(images);
		
		return new ProductDetails(savedProduct);
	}
	
	private List<Image> getAdditionalImages(ProductRequest req) {
		if (req.getAdditionalImages() != null) {
			return req.getAdditionalImages()
				.stream()
				.map(dto -> new Image(dto.name(), null))
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
	public void deleteProduct(Long id, User user) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Could not find product with this id."));
		
		if (!product.getUser().equals(user)) {
			throw new AccessDeniedException("You do not have the rights to delete the current product.");
		}
		
		imageRepository.removeImage(product.getMainImage());
		
		product.getAdditionalImages()
			.stream()
			.map(Image::getNameInBucket)
			.forEach(imageRepository::removeImage);
		
		productRepository.deleteById(id);
	}
	
	@Override
	public ProductDetails updateProduct(Long id, ProductRequest req, User user) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Could not find product with this id."));
		
		if (!product.getUser().equals(user)) {
			throw new AccessDeniedException("You do not have the rights to update the current product.");
		}
		
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
	
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(() ->
			new EntityNotFoundException("Product with id: " + id + " was not found"));
	}
	
	@Transactional
	public void deductStock(Long productId, int quantity) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException("Product not found"));
		
		if (product.getQuantity() < quantity) {
			throw new OutOfStockException("Low stock for: " + product.getName());
		}
		
		product.setQuantity(product.getQuantity() - quantity);
		productRepository.save(product);
	}
}