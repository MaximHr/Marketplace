package com.fmi.springcourse.marketplace.controller;

import com.fmi.springcourse.marketplace.dto.product.ProductDetails;
import com.fmi.springcourse.marketplace.dto.product.ProductRequest;
import com.fmi.springcourse.marketplace.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<ProductDetails> createProduct(@Valid @RequestBody ProductRequest product, Errors errors) {
		System.out.println(errors.getFieldErrors());
		
		ProductDetails uploadedProduct = service.createProduct(product);
		
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(uploadedProduct);
	}
	
}
