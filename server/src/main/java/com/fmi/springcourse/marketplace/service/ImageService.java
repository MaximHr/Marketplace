package com.fmi.springcourse.marketplace.service;

import com.fmi.springcourse.marketplace.dto.ImageUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
	ImageUploadDto uploadImages(List<MultipartFile> images);
	
	void removeImage(String id);
}
