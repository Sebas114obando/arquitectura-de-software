package com.ejemplo.catalogo.adapter.out.persistence.persistence;

import com.ejemplo.catalogo.domain.model.Pedido;
import com.ejemplo.catalogo.domain.model.PedidoEstado;
import com.ejemplo.catalogo.domain.port.out.PedidoRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class PedidoPersistenceAdapter implements PedidoRepository {

    private final PedidoJpaRepository repository;
    private final AtomicLong idSequence = new AtomicLong(0);
    private boolean initialized = false;

    public PedidoPersistenceAdapter(PedidoJpaRepository repository) {
        this.repository = repository;
    }

    private synchronized void ensureInitialized() {
        if (!initialized) {
            long maxId = repository.findMaxId();
            idSequence.set(maxId);
            initialized = true;
        }
    }

    @Override
    public synchronized long nextId() {
        ensureInitialized();
        return idSequence.incrementAndGet();
    }

    @Override
    @Transactional
    public Pedido save(Pedido pedido) {
        PedidoJpaEntity entity = toEntity(pedido);
        PedidoJpaEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return repository.findById(id).map(PedidoPersistenceAdapter::toDomain);
    }

    @Override
    public List<Pedido> findAll() {
        return repository.findAll().stream()
                .map(PedidoPersistenceAdapter::toDomain)
                .toList();
    }

    @Override
    public List<Pedido> findByClienteId(String clienteId) {
        return repository.findByClienteId(clienteId).stream()
                .map(PedidoPersistenceAdapter::toDomain)
                .toList();
    }

    private static Pedido toDomain(PedidoJpaEntity entity) {
        return new Pedido(
                entity.getId(),
                entity.getClienteId(),
                entity.getProductoId(),
                entity.getCantidad(),
                PedidoEstado.valueOf(entity.getEstado()),
                entity.getTotal()
        );
    }

    private static PedidoJpaEntity toEntity(Pedido domain) {
        return new PedidoJpaEntity(
                domain.getId(),
                domain.getClienteId(),
                domain.getProductoId(),
                domain.getCantidad(),
                domain.getEstado().name(),
                domain.getTotal()
        );
    }
}
