package com.fmi.springcourse.marketplace.repository;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageRepository {
	List<String> uploadMultipleImages(List<MultipartFile> images);
	
	String singleImageUpload(MultipartFile img);
	
	void  removeImage(String id);
}
