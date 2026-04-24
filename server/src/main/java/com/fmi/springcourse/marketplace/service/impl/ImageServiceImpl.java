package com.fmi.springcourse.marketplace.service.impl;

import com.fmi.springcourse.marketplace.dto.ImageUploadDto;
import com.fmi.springcourse.marketplace.exception.ImageUploadException;
import com.fmi.springcourse.marketplace.repository.ImageRepository;
import com.fmi.springcourse.marketplace.service.ImageService;
import com.fmi.springcourse.marketplace.util.FileTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
	@Value("${spring.servlet.multipart.max-file-size}")
	private DataSize maxImageSize;
	
	private final ImageRepository repository;
	
	public ImageServiceImpl(ImageRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public ImageUploadDto uploadImages(List<MultipartFile> images) {
		for (MultipartFile img : images) {
			if (!isCorrectFileSize(img)) {
				throw new ImageUploadException("Image "
					+ img.getName()
					+ " size must be at most "
					+ maxImageSize.toMegabytes()
				);
			}
			
			if (!FileTypeValidator.isAllowedImage(img)) {
				throw new ImageUploadException("Invalid content type.");
			}
		}
		
		if (images.size() == 1) {
			var ids = List.of(
				repository.singleImageUpload(images.getFirst())
			);
			return new ImageUploadDto(ids);
		}
		
		var ids = repository.uploadMultipleImages(images);
		
		return new ImageUploadDto(ids);
	}
	
	private boolean isCorrectFileSize(MultipartFile file) {
		return file.getSize() <= maxImageSize.toBytes();
	}
	
	@Override
	public void removeImage(String id) {
		repository.removeImage(id);
	}
}
