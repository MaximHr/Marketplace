package com.fmi.springcourse.marketplace.product;

import com.fmi.springcourse.marketplace.dto.PageResponse;
import com.fmi.springcourse.marketplace.dto.StringResponse;
import com.fmi.springcourse.marketplace.product.dto.ProductCardDto;
import com.fmi.springcourse.marketplace.product.dto.ProductDetails;
import com.fmi.springcourse.marketplace.product.dto.ProductRequest;
import com.fmi.springcourse.marketplace.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
	private final ProductService service;
	
	public ProductController(ProductService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<ProductDetails> createProduct(@Valid @RequestBody ProductRequest product) {
		ProductDetails uploadedProduct = service.createProduct(product);
		
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(uploadedProduct);
	}
	
	@GetMapping("/{slug}")
	public ResponseEntity<ProductDetails> getProductBySlug(@PathVariable String slug) {
		ProductDetails product = service.getProductDetailsBySlug(slug);
		
		return ResponseEntity.ok(product);
	}
	
	@GetMapping
	public ResponseEntity<PageResponse<ProductCardDto>> listProducts(Pageable pageable) {
		return ResponseEntity.ok(service.listProducts(pageable));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDetails> updateProduct(
		@PathVariable Long id,
		@Valid @RequestBody ProductRequest product
	) {
		ProductDetails updatedProduct = service.updateProduct(id, product);
		
		return ResponseEntity.ok(updatedProduct);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<StringResponse> deleteProduct(@PathVariable Long id) {
		service.deleteProduct(id);
		
		return ResponseEntity.ok(new StringResponse("Product deleted successfully"));
	}
}
