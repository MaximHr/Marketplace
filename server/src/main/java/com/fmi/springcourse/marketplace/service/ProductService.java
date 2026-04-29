package com.fmi.springcourse.marketplace.service;

import com.fmi.springcourse.marketplace.dto.PageResponse;
import com.fmi.springcourse.marketplace.dto.product.ProductCardDto;
import com.fmi.springcourse.marketplace.dto.product.ProductDetails;
import com.fmi.springcourse.marketplace.dto.product.ProductRequest;
import org.springframework.data.domain.Pageable;

public interface ProductService {
	ProductDetails createProduct(ProductRequest product);
	
	ProductDetails getProductDetailsBySlug(String slug);
	
	PageResponse<ProductCardDto> listProducts(Pageable pageable);

	void deleteProduct(Long id);
}
