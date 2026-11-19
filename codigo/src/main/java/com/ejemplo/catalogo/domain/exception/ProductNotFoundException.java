package com.ejemplo.catalogo.domain.exception;

/**
 * Domain exception thrown by the use cases when a product does not exist.
 * The web adapter translates it into an HTTP 404 response.
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("No existe el producto con id " + id);
    }
}

