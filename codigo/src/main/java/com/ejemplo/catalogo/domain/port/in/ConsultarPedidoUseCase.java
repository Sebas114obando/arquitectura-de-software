package com.ejemplo.catalogo.domain.port.in;

import com.ejemplo.catalogo.domain.model.Pedido;
import java.util.Optional;

/**
 * Puerto de entrada para consultas de pedidos (RN-4).
 */
public interface ConsultarPedidoUseCase {

    Optional<Pedido> consultarPorId(Long id);

    long consultarTotalCompradoPorCliente(String clienteId);
}
