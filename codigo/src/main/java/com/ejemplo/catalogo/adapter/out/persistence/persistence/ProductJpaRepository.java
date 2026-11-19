package com.ejemplo.catalogo.adapter.out.persistence.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository over {@link ProductJpaEntity}. Internal to the
 * persistence adapter: only ProductPersistenceAdapter and DataSeeder use it.
 */
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
    // Spring Data derives the query from the method name:
    // SELECT * FROM products WHERE LOWER(name) LIKE LOWER('%texto%')
    List<ProductJpaEntity> findByNameContainingIgnoreCase(String name);
}

