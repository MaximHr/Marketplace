package com.fmi.springcourse.marketplace.cart.dto;

import com.fmi.springcourse.marketplace.cart.entity.Cart;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(Long id,
                           String userProfile,
                           List<CartItemResponse> cartItems,
                           BigDecimal totalAmount) {
    public CartResponse(Cart cart) {
        this(
            cart.getId(),
            cart.getUser().getProfileName(),
            cart.getCartItems().stream()
                    .map(CartItemResponse::new)
                    .toList(),
            cart.getTotalAmount()
        );
    }
}
