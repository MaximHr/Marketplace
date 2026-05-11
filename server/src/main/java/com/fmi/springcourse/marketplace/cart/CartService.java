package com.fmi.springcourse.marketplace.cart;

import com.fmi.springcourse.marketplace.cart.dto.CartResponse;
import com.fmi.springcourse.marketplace.cart.entity.Cart;
import com.fmi.springcourse.marketplace.product.entity.Product;
import com.fmi.springcourse.marketplace.exception.EntityNotFoundException;
import com.fmi.springcourse.marketplace.exception.OutOfStockException;
import com.fmi.springcourse.marketplace.product.service.ProductServiceImpl;
import com.fmi.springcourse.marketplace.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository repo;
    private final ProductServiceImpl productService;

    @Transactional
    public CartResponse addProduct(Long productId, Integer quantity, User user) {
        Cart cart = repo.findByUser(user).orElse(new Cart(user));
        Product product = productService.getProductById(productId);
        cart.addItem(product, quantity);
        repo.save(cart);

        return new CartResponse(cart);
    }

    @Transactional
    public CartResponse removeProduct(Long productId, User user) {
        return repo.findByUser(user).map(cart -> {
            Product product = productService.getProductById(productId);
            cart.removeItem(product);
            repo.save(cart);
            return new CartResponse(cart);
        }).orElseGet(() -> new CartResponse(new Cart(user))); // Return empty representation
    }

    @Transactional
    public CartResponse updateQuantity(Long productId, Integer requestedQuantity, User user) {
        Product product = productService.getProductById(productId);
        if (requestedQuantity > product.getQuantity()) {
            throw new OutOfStockException("Only " + product.getQuantity() + " items left in stock");
        }

        Cart cart = repo.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        cart.updateQuantity(product, requestedQuantity);
        repo.save(cart);
        return new CartResponse(cart);
    }

    @Transactional
    public void emptyUserCart(User user) {
        repo.findByUser(user).ifPresent(cart -> {
            cart.getCartItems().clear();
            repo.save(cart);
        });
    }

    public Cart getCartByUser(User user) {
        return repo.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("User cart not found"));
    }
}
