package com.fmi.springcourse.marketplace.order.dto;

import com.fmi.springcourse.marketplace.cart.dto.CartItemResponse;
import com.fmi.springcourse.marketplace.cart.entity.Cart;
import com.fmi.springcourse.marketplace.order.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Long id,
                            String userProfile,
                            List<OrderItemResponse> cartItems,
                            BigDecimal totalAmount) {
    public OrderResponse(Order order) {
        this(
                order.getId(),
                order.getUser().getProfileName(),
                order.getOrderItems().stream()
                        .map(OrderItemResponse::new)
                        .toList(),
                order.getTotalAmount()
        );
    }
}
