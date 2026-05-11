package com.fmi.springcourse.marketplace.product.service;

import com.fmi.springcourse.marketplace.dto.PageResponse;
import com.fmi.springcourse.marketplace.product.dto.ProductCardDto;
import com.fmi.springcourse.marketplace.product.dto.ProductDetails;
import com.fmi.springcourse.marketplace.product.dto.ProductRequest;
import org.springframework.data.domain.Pageable;

public interface ProductService {
	ProductDetails createProduct(ProductRequest product);
	
	ProductDetails getProductDetailsBySlug(String slug);
	
	PageResponse<ProductCardDto> listProducts(Pageable pageable);

	void deleteProduct(Long id);
	
	ProductDetails updateProduct(Long id, ProductRequest req);
}
