package com.fmi.springcourse.marketplace.cart.dto;

import com.fmi.springcourse.marketplace.cart.entity.CartItem;

import java.math.BigDecimal;

public record CartItemResponse(
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity
) {
    public CartItemResponse(CartItem item) {
        this(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getQuantity()
        );
    }
}
