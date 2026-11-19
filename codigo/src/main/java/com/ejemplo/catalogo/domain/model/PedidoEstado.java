package com.ejemplo.catalogo.domain.model;

/**
 * Estado final de un pedido.
 * Según las reglas RN-3 y RN-5:
 * - CONFIRMADO: pedido válido con stock suficiente.
 * - RECHAZADO: pedido válido pero con stock insuficiente.
 * (Los errores de validación no se registran ni reciben id).
 */
public enum PedidoEstado {
    CONFIRMADO,
    RECHAZADO
}
