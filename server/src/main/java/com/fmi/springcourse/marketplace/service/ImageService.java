package com.fmi.springcourse.marketplace.service;

import com.fmi.springcourse.marketplace.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
	List<ImageDto> uploadImages(List<MultipartFile> images, Long productId);
	
	void removeImage(String nameInBucket);
}
