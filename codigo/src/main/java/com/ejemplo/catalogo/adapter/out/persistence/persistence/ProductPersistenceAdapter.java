package com.ejemplo.catalogo.adapter.out.persistence.persistence;

import com.ejemplo.catalogo.domain.model.Product;
import com.ejemplo.catalogo.domain.port.out.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ProductPersistenceAdapter implements ProductRepository {

    private final ProductJpaRepository repository;

    public ProductPersistenceAdapter(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream()
                .map(ProductPersistenceAdapter::toDomain)
                .toList();
    }

    @Override
    public List<Product> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name).stream()
                .map(ProductPersistenceAdapter::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id)
                .map(ProductPersistenceAdapter::toDomain);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        ProductJpaEntity saved = repository.save(toEntity(product));
        return toDomain(saved);
    }

    @Override
    @Transactional
    public void delete(Product product) {
        repository.deleteById(product.getId());
    }

    // JPA entity -> domain model
    private static Product toDomain(ProductJpaEntity entity) {
        Product product = new Product(
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock(),
                entity.getCategory());
        product.setId(entity.getId());
        return product;
    }

    // Domain model -> JPA entity (keeps the id so updates hit the same row)
    private static ProductJpaEntity toEntity(Product product) {
        ProductJpaEntity entity = new ProductJpaEntity(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory());
        entity.setId(product.getId());
        return entity;
    }
}

