package com.ejemplo.catalogo.application.usecase;

import com.ejemplo.catalogo.domain.exception.ValidationException;
import com.ejemplo.catalogo.domain.model.Pedido;
import com.ejemplo.catalogo.domain.model.PedidoEstado;
import com.ejemplo.catalogo.domain.model.Product;
import com.ejemplo.catalogo.domain.port.in.ConsultarPedidoUseCase;
import com.ejemplo.catalogo.domain.port.in.CrearPedidoUseCase;
import com.ejemplo.catalogo.domain.port.out.PedidoRepository;
import com.ejemplo.catalogo.domain.port.out.ProductRepository;
import com.ejemplo.catalogo.domain.service.DiscountPolicy;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de aplicación que implementa los casos de uso de pedidos.
 * Orquesta el flujo respetando las reglas de negocio RN-1 a RN-5.
 */
@Service
@Transactional
public class PedidoUseCaseService implements CrearPedidoUseCase, ConsultarPedidoUseCase {

    private final ProductRepository productRepository;
    private final PedidoRepository pedidoRepository;
    private final DiscountPolicy discountPolicy;

    public PedidoUseCaseService(ProductRepository productRepository,
                                PedidoRepository pedidoRepository) {
        this.productRepository = productRepository;
        this.pedidoRepository = pedidoRepository;
        this.discountPolicy = new DiscountPolicy();
    }

    @Override
    public Pedido crearPedido(String clienteId, Long productoId, int cantidad) {
        // RN-5: Validación. Cantidad <= 0 o productoId inexistente es error de validación.
        // No es un pedido RECHAZADO: no se registra y no consume id.
        if (cantidad <= 0) {
            throw new ValidationException("La cantidad debe ser mayor a cero");
        }
        if (productoId == null) {
            throw new ValidationException("El identificador de producto no puede ser nulo");
        }

        Product product = productRepository.findById(productoId)
                .orElseThrow(() -> new ValidationException("El producto no existe: " + productoId));

        // Pasa la validación: se le asigna id consecutivo desde 1
        long id = pedidoRepository.nextId();

        // RN-3: Stock.
        // Si no alcanza el stock, queda RECHAZADO, total=0 y no consume stock.
        if (!product.hasSufficientStock(cantidad)) {
            Pedido pedidoRechazado = new Pedido(
                    id,
                    clienteId,
                    productoId,
                    cantidad,
                    PedidoEstado.RECHAZADO,
                    0L
            );
            return pedidoRepository.save(pedidoRechazado);
        }

        // Si alcanza el stock, se descuenta stock y se confirma
        product.deductStock(cantidad);
        productRepository.save(product);

        // RN-1 y RN-2: Cálculo de descuento y total
        int pctDescuento = discountPolicy.calcularPorcentajeDescuento(cantidad);
        long precioUnitario = product.getPrice().longValue();
        long total = discountPolicy.calcularTotal(cantidad, precioUnitario, pctDescuento);

        Pedido pedidoConfirmado = new Pedido(
                id,
                clienteId,
                productoId,
                cantidad,
                PedidoEstado.CONFIRMADO,
                total
        );
        return pedidoRepository.save(pedidoConfirmado);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pedido> consultarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long consultarTotalCompradoPorCliente(String clienteId) {
        return pedidoRepository.findByClienteId(clienteId).stream()
                .filter(p -> p.getEstado() == PedidoEstado.CONFIRMADO)
                .mapToLong(Pedido::getTotal)
                .sum();
    }
}
