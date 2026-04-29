package com.fmi.springcourse.marketplace.service;

import com.fmi.springcourse.marketplace.dto.product.ProductDetails;
import com.fmi.springcourse.marketplace.dto.product.ProductRequest;

public interface ProductService {
	ProductDetails createProduct(ProductRequest product);
}
