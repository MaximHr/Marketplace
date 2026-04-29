package com.fmi.springcourse.marketplace.dto.product;

import com.fmi.springcourse.marketplace.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductCardDto(String name, BigDecimal price, String slug, LocalDateTime createdAt) {
	public ProductCardDto(Product product) {
		this(product.getName(), product.getPrice(), product.getSlug(), product.getCreatedAt());
	}
}
