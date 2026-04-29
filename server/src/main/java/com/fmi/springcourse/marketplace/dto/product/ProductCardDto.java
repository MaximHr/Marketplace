package com.fmi.springcourse.marketplace.dto.product;

import com.fmi.springcourse.marketplace.entity.Product;

import java.math.BigDecimal;

public record ProductCardDto(String name, BigDecimal price, String slug) {
	public ProductCardDto(Product product) {
		this(product.getName(), product.getPrice(), product.getSlug());
	}
}
