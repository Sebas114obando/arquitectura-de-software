package com.ejemplo.catalogo.adapter.in.web.controller;

import com.ejemplo.catalogo.adapter.in.web.dto.ProductRequest;
import com.ejemplo.catalogo.adapter.in.web.dto.ProductResponse;
import com.ejemplo.catalogo.adapter.in.web.mapper.ProductMapper;
import com.ejemplo.catalogo.domain.model.Product;
import com.ejemplo.catalogo.domain.port.in.ProductService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // GET /api/products or GET /api/products?q=laptop
    @GetMapping
    public List<ProductResponse> findAll(
            @RequestParam(required = false) String q) {
        return service.findAll(q).stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    // GET /api/products/1
    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return ProductMapper.toResponse(service.findById(id));
    }

    // POST /api/products -> 201 Created + Location header
    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductRequest request) {
        Product created = service.create(ProductMapper.toDomain(request));
        ProductResponse response = ProductMapper.toResponse(created);
        URI location = URI.create("/api/products/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    // PUT /api/products/1
    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id,
                                  @Valid @RequestBody ProductRequest request) {
        Product updated = service.update(id, ProductMapper.toDomain(request));
        return ProductMapper.toResponse(updated);
    }

    // DELETE /api/products/1 -> 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

