package com.fmi.springcourse.marketplace.entity;

import com.fmi.springcourse.marketplace.vo.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {
	public static final int DESCRIPTION_MAX_LENGTH = 2000;
	private static final int PRICE_PRECISION = 10;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String slug;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, length = DESCRIPTION_MAX_LENGTH)
	private String description;
	
	@Column(nullable = false, precision = PRICE_PRECISION, scale = 2)
	private BigDecimal price;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Enumerated(EnumType.STRING)
	private ProductType type;
	
	protected Product() {
	}
	
	public Product(String name, String description, BigDecimal price, Integer quantity, ProductType type) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.type = type;
	}
	
	@PrePersist
	public void prePersist() {
		if (slug == null || slug.isBlank()) {
			slug = UUID.randomUUID().toString();
		}
	}
	
	public Long getId() {
		return id;
	}
	
	public String getSlug() {
		return slug;
	}
	
	public String getName() {
		return name;
	}
	
	public ProductType getType() {
		return type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}