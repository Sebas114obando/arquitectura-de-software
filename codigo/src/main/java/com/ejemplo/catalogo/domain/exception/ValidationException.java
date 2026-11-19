package com.ejemplo.catalogo.domain.exception;

/**
 * Excepción de dominio para errores de validación según RN-5.
 * Representa pedidos inválidos (cantidad <= 0 o productoId inexistente).
 * No genera id ni se persiste como pedido RECHAZADO.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
