package com.ejemplo.catalogo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ejemplo.catalogo.domain.model.Pedido;
import com.ejemplo.catalogo.domain.model.PedidoEstado;
import com.ejemplo.catalogo.domain.port.in.CrearPedidoUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Muestra oficial de la Hoja 1 para la Fila 3:
 * Monitor ($600.000), stock inicial 24, u = 3, d = 12%.
 */
@SpringBootTest
public class MuestraHoja1Test {

    @Autowired
    private CrearPedidoUseCase crearPedidoUseCase;

    @Test
    @DisplayName("Ejecución oficial de la muestra Hoja 1")
    public void ejecutarMuestraHoja1() {
        // Línea 1: C-001, u - 1 = 2
        Pedido p1 = crearPedidoUseCase.crearPedido("C-001", 1L, 2);
        System.out.println("PEDIDO " + p1.getId() + " " + p1.getEstado() + " total=" + p1.getTotal());
        assertEquals(1L, p1.getId());
        assertEquals(PedidoEstado.CONFIRMADO, p1.getEstado());
        assertEquals(1200000L, p1.getTotal());

        // Línea 2: C-002, u = 3
        Pedido p2 = crearPedidoUseCase.crearPedido("C-002", 1L, 3);
        System.out.println("PEDIDO " + p2.getId() + " " + p2.getEstado() + " total=" + p2.getTotal());
        assertEquals(2L, p2.getId());
        assertEquals(PedidoEstado.CONFIRMADO, p2.getEstado());
        assertEquals(1584000L, p2.getTotal());

        // Línea 3: C-001, stock - 2u + 2 = 20
        Pedido p3 = crearPedidoUseCase.crearPedido("C-001", 1L, 20);
        System.out.println("PEDIDO " + p3.getId() + " " + p3.getEstado() + " total=" + p3.getTotal());
        assertEquals(3L, p3.getId());
        assertEquals(PedidoEstado.RECHAZADO, p3.getEstado());
        assertEquals(0L, p3.getTotal());

        // Línea 4: C-003, stock - 2u + 1 = 19
        Pedido p4 = crearPedidoUseCase.crearPedido("C-003", 1L, 19);
        System.out.println("PEDIDO " + p4.getId() + " " + p4.getEstado() + " total=" + p4.getTotal());
        assertEquals(4L, p4.getId());
        assertEquals(PedidoEstado.CONFIRMADO, p4.getEstado());
        assertEquals(10032000L, p4.getTotal());
    }
}
