package com.ejemplo.catalogo.domain.port.in;

import com.ejemplo.catalogo.domain.model.Product;
import java.util.List;

/**
 * Driving port (input): the use-case operations the web adapter can call.
 * Expressed purely in domain terms (domain {@link Product}, no DTOs).
 */
public interface ProductService {

    /** Lists all products, or filters by name when {@code q} is non-blank. */
    List<Product> findAll(String q);

    /** Finds one product by id or throws ProductNotFoundException (404). */
    Product findById(Long id);

    /** Creates a new product and returns it with the assigned id. */
    Product create(Product product);

    /** Updates an existing product or throws ProductNotFoundException (404). */
    Product update(Long id, Product changes);

    /** Deletes an existing product or throws ProductNotFoundException (404). */
    void delete(Long id);
}

