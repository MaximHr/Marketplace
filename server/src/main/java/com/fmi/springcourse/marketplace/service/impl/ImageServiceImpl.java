package com.fmi.springcourse.marketplace.service.impl;

import com.fmi.springcourse.marketplace.dto.ImageUploadDto;
import com.fmi.springcourse.marketplace.repository.ImageRepository;
import com.fmi.springcourse.marketplace.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
	private final ImageRepository repository;
	
	public ImageServiceImpl(ImageRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public ImageUploadDto uploadImages(List<MultipartFile> images) {
		if (images.size() == 1) {
			var ids = List.of(
				repository.singleImageUpload(images.getFirst())
			);
			
			return new ImageUploadDto(ids);
		}
		
		var ids = repository.uploadMultipleImages(images);
		
		return new ImageUploadDto(ids);
	}
	
	@Override
	public void removeImage(String id) {
	
	}
}
