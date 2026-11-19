package com.ejemplo.catalogo.adapter.in.web.mapper;

import com.ejemplo.catalogo.adapter.in.web.dto.ProductRequest;
import com.ejemplo.catalogo.adapter.in.web.dto.ProductResponse;
import com.ejemplo.catalogo.domain.model.Product;

public final class ProductMapper {

    private ProductMapper() {
        // utility class: never instantiated
    }

    // Request DTO -> new domain product
    public static Product toDomain(ProductRequest request) {
        return new Product(
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.category());
    }

    // Domain product -> response DTO
    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory());
    }
}

