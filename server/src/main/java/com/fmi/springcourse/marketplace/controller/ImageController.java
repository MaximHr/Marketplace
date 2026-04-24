package com.fmi.springcourse.marketplace.controller;

import com.fmi.springcourse.marketplace.dto.ExceptionResponse;
import com.fmi.springcourse.marketplace.dto.ImageUploadDto;
import com.fmi.springcourse.marketplace.dto.StringResponse;
import com.fmi.springcourse.marketplace.exception.ImageDeletionException;
import com.fmi.springcourse.marketplace.exception.ImageUploadException;
import com.fmi.springcourse.marketplace.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {
	private final ImageService service;
	
	public ImageController(ImageService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<ImageUploadDto> upload(@RequestParam("images") List<MultipartFile> images) {
		var ids = service.uploadImages(images);
		
		return ResponseEntity.ok(ids);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<StringResponse> removeHandler(@PathVariable String id) {
		service.removeImage(id);
		
		return ResponseEntity.ok(
			new StringResponse("Image deleted successfully")
		);
	}
	
	@ExceptionHandler(ImageUploadException.class)
	public ResponseEntity<ExceptionResponse> imageUploadExceptionHandler(ImageUploadException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ExceptionResponse(e.getMessage()));
	}
	
	@ExceptionHandler(ImageUploadException.class)
	public ResponseEntity<ExceptionResponse> imageUploadExceptionHandler(ImageDeletionException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ExceptionResponse(e.getMessage()));
	}
	
}
