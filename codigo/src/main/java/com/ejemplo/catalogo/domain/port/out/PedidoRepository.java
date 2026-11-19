package com.ejemplo.catalogo.domain.port.out;

import com.ejemplo.catalogo.domain.model.Pedido;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para persistencia de pedidos.
 * Definido en el dominio sin depender de detalles de base de datos.
 */
public interface PedidoRepository {

    Pedido save(Pedido pedido);

    Optional<Pedido> findById(Long id);

    List<Pedido> findAll();

    List<Pedido> findByClienteId(String clienteId);

    /**
     * Genera el siguiente id consecutivo para un pedido validado (RN-5).
     */
    long nextId();
}
