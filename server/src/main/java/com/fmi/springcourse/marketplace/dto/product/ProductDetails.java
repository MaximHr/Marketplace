package com.fmi.springcourse.marketplace.dto.product;

import com.fmi.springcourse.marketplace.dto.ImageDto;
import com.fmi.springcourse.marketplace.entity.Product;
import com.fmi.springcourse.marketplace.vo.ProductType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductDetails(Long id,
                             String slug,
                             String name,
                             String description,
                             BigDecimal price,
                             Integer quantity,
                             ProductType type,
                             LocalDateTime createdAt,
                             String mainImage,
                             List<ImageDto> additionalImages) {
	public ProductDetails(Product product) {
		var images = product.getAdditionalImages()
			.stream()
			.map(ImageDto::new)
			.toList();
		
		this(product.getId(), product.getSlug(), product.getName(), product.getDescription(),
			product.getPrice(), product.getQuantity(), product.getType(), product.getCreatedAt(),
			product.getMainImage(), images
		);
	}
}
