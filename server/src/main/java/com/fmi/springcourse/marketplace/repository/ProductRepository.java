package com.fmi.springcourse.marketplace.repository;

import com.fmi.springcourse.marketplace.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> getBySlug(String slug);
}
