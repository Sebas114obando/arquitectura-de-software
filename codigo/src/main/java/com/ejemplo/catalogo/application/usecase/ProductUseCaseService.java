package com.ejemplo.catalogo.application.usecase;

import com.ejemplo.catalogo.domain.exception.ProductNotFoundException;
import com.ejemplo.catalogo.domain.model.Product;
import com.ejemplo.catalogo.domain.port.in.ProductService;
import com.ejemplo.catalogo.domain.port.out.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use-case implementation of the driving port {@link ProductService}.
 *
 * <p>Depends ONLY on the driven domain port {@link ProductRepository}: it knows
 * nothing about HTTP controllers, DTOs or JPA. Transactionality is preserved
 * from the original layered service via Spring's {@code @Transactional}.
 */
@Service
@Transactional(readOnly = true)
public class ProductUseCaseService implements ProductService {

    private final ProductRepository repository;

    public ProductUseCaseService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAll(String q) {
        if (q == null || q.isBlank()) {
            return repository.findAll();
        }
        return repository.findByNameContainingIgnoreCase(q);
    }

    @Override
    public Product findById(Long id) {
        return getOrThrow(id);
    }

    @Override
    @Transactional
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    @Transactional
    public Product update(Long id, Product changes) {
        Product product = getOrThrow(id);
        product.setName(changes.getName());
        product.setDescription(changes.getDescription());
        product.setPrice(changes.getPrice());
        product.setStock(changes.getStock());
        product.setCategory(changes.getCategory());
        return repository.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = getOrThrow(id);
        repository.delete(product);
    }

    // Finds by id or throws 404 (translated by GlobalExceptionHandler)
    private Product getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}

