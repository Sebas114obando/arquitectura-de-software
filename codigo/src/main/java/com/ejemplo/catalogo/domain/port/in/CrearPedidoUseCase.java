package com.ejemplo.catalogo.domain.port.in;

import com.ejemplo.catalogo.domain.model.Pedido;

/**
 * Puerto de entrada para el caso de uso de registrar/crear pedido.
 */
public interface CrearPedidoUseCase {

    Pedido crearPedido(String clienteId, Long productoId, int cantidad);
}
