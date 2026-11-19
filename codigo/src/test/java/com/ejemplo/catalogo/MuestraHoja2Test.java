package com.ejemplo.catalogo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ejemplo.catalogo.domain.model.Pedido;
import com.ejemplo.catalogo.domain.model.PedidoEstado;
import com.ejemplo.catalogo.domain.port.in.CrearPedidoUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Muestra oficial de la Hoja 2 para la variante Q1 / Fila 3:
 * Monitor ($600.000), stock 24, u = 3.
 * Tramo nuevo: cantidad >= 3u (9) recibe d + 5 = 17%.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MuestraHoja2Test {

    @Autowired
    private CrearPedidoUseCase crearPedidoUseCase;

    @Test
    @DisplayName("Ejecución oficial de la muestra Hoja 2")
    public void ejecutarMuestraHoja2() {
        // Línea 1: C-001, 3u = 9 unidades (tramo nuevo 17%)
        Pedido p1 = crearPedidoUseCase.crearPedido("C-001", 1L, 9);
        System.out.println("PEDIDO " + p1.getId() + " " + p1.getEstado() + " total=" + p1.getTotal());
        assertEquals(1L, p1.getId());
        assertEquals(PedidoEstado.CONFIRMADO, p1.getEstado());
        assertEquals(4482000L, p1.getTotal());

        // Línea 2: C-002, 3u - 1 = 8 unidades (tramo base 12%)
        Pedido p2 = crearPedidoUseCase.crearPedido("C-002", 1L, 8);
        System.out.println("PEDIDO " + p2.getId() + " " + p2.getEstado() + " total=" + p2.getTotal());
        assertEquals(2L, p2.getId());
        assertEquals(PedidoEstado.CONFIRMADO, p2.getEstado());
        assertEquals(4224000L, p2.getTotal());

        // Línea 3: C-003, 1 unidad (sin descuento 0%)
        Pedido p3 = crearPedidoUseCase.crearPedido("C-003", 1L, 1);
        System.out.println("PEDIDO " + p3.getId() + " " + p3.getEstado() + " total=" + p3.getTotal());
        assertEquals(3L, p3.getId());
        assertEquals(PedidoEstado.CONFIRMADO, p3.getEstado());
        assertEquals(600000L, p3.getTotal());
    }
}
