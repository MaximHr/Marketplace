package com.fmi.springcourse.marketplace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(
	name = "images"
)
public class Image {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Getter
	@Column(unique = true, nullable = false)
	private String nameInBucket;
	
	protected Image() {
	}
	
	public Image(String nameInBucket) {
		this.nameInBucket = nameInBucket;
	}
}
