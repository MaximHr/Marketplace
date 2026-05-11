package com.fmi.springcourse.marketplace.order;

import com.fmi.springcourse.marketplace.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
