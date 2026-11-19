package com.ejemplo.catalogo.adapter.out.persistence.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoJpaRepository extends JpaRepository<PedidoJpaEntity, Long> {

    List<PedidoJpaEntity> findByClienteId(String clienteId);

    @Query("SELECT COALESCE(MAX(p.id), 0) FROM PedidoJpaEntity p")
    long findMaxId();
}
