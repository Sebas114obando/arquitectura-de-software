package com.ejemplo.catalogo.domain.port.out;

import com.ejemplo.catalogo.domain.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Driven port (output): the persistence needs of the application layer,
 * expressed in domain terms (returns domain {@link Product}, not JPA entities).
 * Implemented by the persistence adapter (ProductPersistenceAdapter).
 */
public interface ProductRepository {

    /** Returns every product. */
    List<Product> findAll();

    /** SELECT * FROM products WHERE LOWER(name) LIKE LOWER('%texto%') */
    List<Product> findByNameContainingIgnoreCase(String name);

    /** Returns the product with that id, if it exists. */
    Optional<Product> findById(Long id);

    /** Persists the product (insert or update) and returns the saved state. */
    Product save(Product product);

    /** Removes the product from persistence. */
    void delete(Product product);
}

