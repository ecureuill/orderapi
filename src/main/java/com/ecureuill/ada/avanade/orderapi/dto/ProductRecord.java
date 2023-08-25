package com.ecureuill.ada.avanade.orderapi.dto;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.ecureuill.ada.avanade.orderapi.entity.ProductEntity;

public record ProductRecord(
    String title,
    String description,
    BigDecimal price,
    BigDecimal discountPercentage,
    Float rating,
    Integer stock,
    String brand,
    String category,
    String thumbnail,
    ArrayList<String> images
) {
    
    public ProductRecord(ProductEntity product) {
        this(
            product.getTitle(),
            product.getDescription(),
            product.getPrice(),
            product.getDiscountPercentage(),
            product.getRating(),
            product.getStock(),
            product.getBrand(),
            product.getCategory(),
            product.getThumbnail(),
            product.getImages()
        );
    }

    public ProductEntity toEntity(Long id) {
        return new ProductEntity(
            id,
            title,
            description,
            price,
            discountPercentage,
            rating,
            stock,
            brand,
            category,
            thumbnail,
            images
        );
    }
}
