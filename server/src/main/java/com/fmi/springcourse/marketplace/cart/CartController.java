package com.fmi.springcourse.marketplace.cart;

import com.fmi.springcourse.marketplace.cart.dto.CartItemRequest;
import com.fmi.springcourse.marketplace.cart.dto.CartResponse;
import com.fmi.springcourse.marketplace.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService service;

    @PostMapping("/add/{id}")
    public ResponseEntity<CartResponse> addProductToCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody CartItemRequest request
    ) {
        CartResponse cartResponse = service.addProduct(id, request.requestedQuantity(), user);
        return ResponseEntity.ok()
                .body(cartResponse);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<CartResponse> removeProductFromCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        CartResponse cartResponse = service.removeProduct(id, user);
        return ResponseEntity.ok()
                .body(cartResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartResponse> updateQuantity(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody CartItemRequest request
    ) {
        CartResponse cartResponse = service.updateQuantity(id, request.requestedQuantity(), user);
        return ResponseEntity.ok()
                .body(cartResponse);
    }
}
