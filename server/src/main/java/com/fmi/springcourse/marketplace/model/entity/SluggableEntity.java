package com.fmi.springcourse.marketplace.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;

import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class SluggableEntity {
    @Column(nullable = false, unique = true, updatable = false)
    protected String slug;

    @PrePersist
    protected void generateSlug() {
        if (slug == null || slug.isBlank()) {
            slug = UUID.randomUUID().toString();
        }
    }
}
