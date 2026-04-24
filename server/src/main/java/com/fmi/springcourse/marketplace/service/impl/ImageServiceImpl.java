package com.fmi.springcourse.marketplace.service.impl;

import com.fmi.springcourse.marketplace.dto.ImageUploadDto;
import com.fmi.springcourse.marketplace.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
	@Override
	public ImageUploadDto uploadImages(List<MultipartFile> images) {
		List<String> ids = new ArrayList<>();
		
		return new ImageUploadDto(ids);
	}
	
	@Override
	public void removeImage(String id) {
	
	}
}
