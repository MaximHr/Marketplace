package com.fmi.springcourse.marketplace.dto;

import com.fmi.springcourse.marketplace.entity.Image;

public record ImageDto(String name) {
	public ImageDto(Image image) {
		this(image.getNameInBucket());
	}
}
