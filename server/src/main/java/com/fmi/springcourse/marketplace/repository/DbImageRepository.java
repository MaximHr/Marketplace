package com.fmi.springcourse.marketplace.repository;

import com.fmi.springcourse.marketplace.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbImageRepository extends JpaRepository<Image, Long> {
	void deleteByNameInBucket(String nameInBucket);
}
