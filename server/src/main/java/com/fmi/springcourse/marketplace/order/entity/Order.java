package com.fmi.springcourse.marketplace.order.entity;

import com.fmi.springcourse.marketplace.entity.Product;
import com.fmi.springcourse.marketplace.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "orderId", nullable = false)
    private Long id;

    // FK
    @NotNull
    private User user;

    @NotEmpty
    private List<OrderItem> orderItems = new ArrayList<>();

    @NotNull
    @Column(nullable = false)
    private LocalDateTime orderDate;

    @NotNull
    @Column(nullable = false)
    private BigDecimal totalAmount;

    public Order(User user, LocalDateTime orderDate) {
        this.user = user;
        this.orderDate = orderDate;
        this.totalAmount = BigDecimal.ZERO;
    }

    public void addItem(Product product, int quantity) {
        OrderItem item = new OrderItem(this, product, quantity);
        this.orderItems.add(item);
        BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        this.totalAmount = totalAmount.add(itemTotal);
    }
}
