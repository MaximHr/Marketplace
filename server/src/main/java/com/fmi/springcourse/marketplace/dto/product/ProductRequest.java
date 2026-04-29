package com.fmi.springcourse.marketplace.dto.product;

import com.fmi.springcourse.marketplace.entity.Product;
import com.fmi.springcourse.marketplace.vo.ProductType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductRequest {
	@NotBlank(message = "Name can not be blank.")
	private String name;
	
	@NotBlank(message = "Description can not be blank.")
	@Size(max = Product.DESCRIPTION_MAX_LENGTH,
		message = "Description can not be more than " + Product.DESCRIPTION_MAX_LENGTH + " description")
	private String description;
	
	@NotNull
	@DecimalMin(value = "0.00", message = "Price can not be a negative number.")
	private BigDecimal price;
	
	@NotNull
	@Min(value = 0, message = "Quantity can not be a negative number")
	private Integer quantity;
	
	@NotNull
	private ProductType type;
	
	public ProductRequest() {
	}
	
	public String getName() {
		return name;
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
	
	public ProductType getType() {
		return type;
	}
	
	public void setType(ProductType type) {
		this.type = type;
	}
}
