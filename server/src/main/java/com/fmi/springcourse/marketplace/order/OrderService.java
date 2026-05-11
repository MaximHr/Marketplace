package com.fmi.springcourse.marketplace.order;

import com.fmi.springcourse.marketplace.cart.CartRepository;
import com.fmi.springcourse.marketplace.cart.CartService;
import com.fmi.springcourse.marketplace.cart.entity.Cart;
import com.fmi.springcourse.marketplace.cart.entity.CartItem;
import com.fmi.springcourse.marketplace.entity.Product;
import com.fmi.springcourse.marketplace.exception.CartEmptyException;
import com.fmi.springcourse.marketplace.order.dto.OrderResponse;
import com.fmi.springcourse.marketplace.order.entity.Order;
import com.fmi.springcourse.marketplace.service.impl.ProductServiceImpl;
import com.fmi.springcourse.marketplace.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;
    private final CartService cartService;
    private final ProductServiceImpl productService;

    private Order fillOrder(Cart cart) {
        Order order = new Order(cart.getUser(), LocalDateTime.now());

        cart.getCartItems().forEach(item -> order.addItem(item.getProduct(), item.getQuantity()));

        return order;
    }

    @Transactional
    public OrderResponse createOrderFromCart(User user) {
        Cart cart = cartService.getCartByUser(user);

        if (cart.getCartItems().isEmpty()) {
            throw new CartEmptyException("Cannot checkout an empty cart");
        }

        for (CartItem item: cart.getCartItems()) {
            productService.deductStock(item.getProduct().getId(), item.getQuantity());
        }

        Order order = fillOrder(cart);
        Order savedOrder = orderRepo.save(order);

        cartService.emptyUserCart(cart.getUser());

        return new OrderResponse(savedOrder);
    }
}
