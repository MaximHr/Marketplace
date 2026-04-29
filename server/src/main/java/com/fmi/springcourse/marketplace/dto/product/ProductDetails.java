package com.fmi.springcourse.marketplace.dto.product;

import com.fmi.springcourse.marketplace.vo.ProductType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDetails(Long id,
                             String slug,
                             String name,
                             String description,
                             BigDecimal price,
                             Integer quantity,
                             ProductType type,
                             LocalDateTime createdAt) {
}
