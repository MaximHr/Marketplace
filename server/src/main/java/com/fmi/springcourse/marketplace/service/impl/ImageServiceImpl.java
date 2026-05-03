package com.fmi.springcourse.marketplace.service.impl;

import com.fmi.springcourse.marketplace.dto.ImageDto;
import com.fmi.springcourse.marketplace.entity.Image;
import com.fmi.springcourse.marketplace.entity.Product;
import com.fmi.springcourse.marketplace.exception.EntityNotFoundException;
import com.fmi.springcourse.marketplace.exception.ImageUploadException;
import com.fmi.springcourse.marketplace.repository.DbImageRepository;
import com.fmi.springcourse.marketplace.repository.ProductRepository;
import com.fmi.springcourse.marketplace.repository.impl.S3ImageRepository;
import com.fmi.springcourse.marketplace.service.ImageService;
import com.fmi.springcourse.marketplace.util.FileTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
	@Value("${spring.servlet.multipart.max-file-size}")
	private DataSize maxImageSize;
	
	private final S3ImageRepository s3ImageRepository;
	private final DbImageRepository dbImageRepository;
	private final ProductRepository productRepository;
	
	public ImageServiceImpl(S3ImageRepository s3ImageRepository,
	                        DbImageRepository dbImageRepository,
	                        ProductRepository productRepository) {
		this.s3ImageRepository = s3ImageRepository;
		this.dbImageRepository = dbImageRepository;
		this.productRepository = productRepository;
	}
	
	@Transactional
	@Override
	public List<ImageDto> uploadImages(List<MultipartFile> images, Long productId) {
		validateImages(images);
		
		if (images.size() == 1) {
			return uploadSingleImage(images, productId);
		}
		
		var names = s3ImageRepository.uploadMultipleImages(images);
		
		if (productId != null) {
			Product product = productRepository.findById(productId)
				.orElseThrow();
			
			var imgList = names.stream()
				.map(Image::new)
				.toList();
			
			product.getAdditionalImages()
				.addAll(imgList);
			productRepository.save(product);
		}
		
		return names.stream()
			.map(ImageDto::new)
			.toList();
	}
	
	private void validateImages(List<MultipartFile> images) {
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
	}
	
	private List<ImageDto> uploadSingleImage(List<MultipartFile> images, Long productId) {
		String name = s3ImageRepository.singleImageUpload(images.getFirst());
		
		if (productId != null) {
			Product product = productRepository.findById(productId)
				.orElseThrow(() -> new EntityNotFoundException("No such product"));
			
			product.getAdditionalImages()
				.add(new Image(name));
			
			productRepository.save(product);
		}
		
		return List.of(new ImageDto(name));
	}
	
	private boolean isCorrectFileSize(MultipartFile file) {
		return file.getSize() <= maxImageSize.toBytes();
	}
	
	@Transactional
	@Override
	public void removeImage(String nameInBucket) {
		s3ImageRepository.removeImage(nameInBucket);
		dbImageRepository.deleteByNameInBucket(nameInBucket);
	}
}
