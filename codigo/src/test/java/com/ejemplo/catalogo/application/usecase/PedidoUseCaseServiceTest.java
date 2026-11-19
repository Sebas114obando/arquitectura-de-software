package com.ejemplo.catalogo.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ejemplo.catalogo.domain.exception.ValidationException;
import com.ejemplo.catalogo.domain.model.Pedido;
import com.ejemplo.catalogo.domain.model.PedidoEstado;
import com.ejemplo.catalogo.domain.model.Product;
import com.ejemplo.catalogo.domain.port.out.PedidoRepository;
import com.ejemplo.catalogo.domain.port.out.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PedidoUseCaseServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    private PedidoUseCaseService service;

    @BeforeEach
    void setUp() {
        service = new PedidoUseCaseService(productRepository, pedidoRepository);
    }

    @Test
    @DisplayName("RN-5: Cantidad menor o igual a cero lanza ValidationException y no consume id")
    void validacionCantidadInvalida() {
        assertThrows(ValidationException.class, () -> service.crearPedido("C-001", 1L, 0));
        assertThrows(ValidationException.class, () -> service.crearPedido("C-001", 1L, -5));
        verify(pedidoRepository, never()).nextId();
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    @DisplayName("RN-5: Producto inexistente lanza ValidationException y no consume id")
    void validacionProductoInexistente() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> service.crearPedido("C-001", 99L, 2));
        verify(pedidoRepository, never()).nextId();
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    @DisplayName("RN-3: Pedido confirmado descuenta stock y asigna total con RN-1 y RN-2")
    void pedidoConfirmadoDescuentaStock() {
        Product monitor = new Product("Monitor", "Monitor", new BigDecimal("600000.00"), 24, "electronica");
        monitor.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(monitor));
        when(pedidoRepository.nextId()).thenReturn(1L);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(i -> i.getArgument(0));

        Pedido pedido = service.crearPedido("C-001", 1L, 2);

        assertNotNull(pedido);
        assertEquals(1L, pedido.getId());
        assertEquals(PedidoEstado.CONFIRMADO, pedido.getEstado());
        assertEquals(1200000L, pedido.getTotal());
        assertEquals(22, monitor.getStock()); // 24 - 2
        verify(productRepository).save(monitor);
    }

    @Test
    @DisplayName("RN-3: Pedido que supera stock queda RECHAZADO con total=0 y no descuenta stock")
    void pedidoRechazadoPorFaltaDeStock() {
        Product monitor = new Product("Monitor", "Monitor", new BigDecimal("600000.00"), 19, "electronica");
        monitor.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(monitor));
        when(pedidoRepository.nextId()).thenReturn(3L);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(i -> i.getArgument(0));

        Pedido pedido = service.crearPedido("C-001", 1L, 20);

        assertNotNull(pedido);
        assertEquals(3L, pedido.getId());
        assertEquals(PedidoEstado.RECHAZADO, pedido.getEstado());
        assertEquals(0L, pedido.getTotal());
        assertEquals(19, monitor.getStock()); // No consumió stock
        verify(productRepository, never()).save(monitor);
    }

    @Test
    @DisplayName("RN-4: Consulta total comprado por cliente solo suma pedidos CONFIRMADO")
    void consultaTotalClienteSoloConfirmados() {
        Pedido p1 = new Pedido(1L, "C-001", 1L, 2, PedidoEstado.CONFIRMADO, 1200000L);
        Pedido p2 = new Pedido(2L, "C-001", 1L, 20, PedidoEstado.RECHAZADO, 0L);

        when(pedidoRepository.findByClienteId("C-001")).thenReturn(List.of(p1, p2));

        long total = service.consultarTotalCompradoPorCliente("C-001");
        assertEquals(1200000L, total);
    }
}
