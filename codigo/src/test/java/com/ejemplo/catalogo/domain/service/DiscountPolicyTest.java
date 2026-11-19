package com.ejemplo.catalogo.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para las reglas de negocio RN-1 y RN-2 (Hoja 2 - Q1).
 * Variante Fila 3: Monitor ($600.000), u = 3, d = 12%, d_nuevo = 17%.
 */
class DiscountPolicyTest {

    private DiscountPolicy discountPolicy;
    private final long PRECIO_UNITARIO = 600000L;

    @BeforeEach
    void setUp() {
        discountPolicy = new DiscountPolicy();
    }

    @Test
    @DisplayName("RN-1: Cantidad menor al umbral u (u=3) no recibe descuento")
    void sinDescuentoBajoUmbral() {
        int cantidad = 2; // u - 1
        int pct = discountPolicy.calcularPorcentajeDescuento(cantidad);
        assertEquals(0, pct);

        long total = discountPolicy.calcularTotal(cantidad, PRECIO_UNITARIO, pct);
        assertEquals(1200000L, total); // RN-2
    }

    @Test
    @DisplayName("RN-1: Cantidad entre u (3) y 3u - 1 (8) recibe descuento base d=12%")
    void conDescuentoEnTramoBase() {
        int cantidad = 3; // u
        int pct = discountPolicy.calcularPorcentajeDescuento(cantidad);
        assertEquals(12, pct);

        long total = discountPolicy.calcularTotal(cantidad, PRECIO_UNITARIO, pct);
        assertEquals(1584000L, total); // RN-2

        int cantidadLimiteSuperior = 8; // 3u - 1
        int pctLimite = discountPolicy.calcularPorcentajeDescuento(cantidadLimiteSuperior);
        assertEquals(12, pctLimite);
    }

    @Test
    @DisplayName("RN-1 y RN-2: Cantidad mayor o igual a 3u (>=9) recibe nuevo tramo d+5=17%")
    void conDescuentoNuevoTramoHoja2() {
        int cantidadEnUmbral3u = 9; // 3u
        int pct9 = discountPolicy.calcularPorcentajeDescuento(cantidadEnUmbral3u);
        assertEquals(17, pct9);
        long total9 = discountPolicy.calcularTotal(cantidadEnUmbral3u, PRECIO_UNITARIO, pct9);
        assertEquals(4482000L, total9);

        int cantidadLinea4Hoja1 = 19; // stock - 2u + 1
        int pct19 = discountPolicy.calcularPorcentajeDescuento(cantidadLinea4Hoja1);
        assertEquals(17, pct19);
        long total19 = discountPolicy.calcularTotal(cantidadLinea4Hoja1, PRECIO_UNITARIO, pct19);
        assertEquals(9462000L, total19); // Valor verificado de Hoja 2 para fila 3
    }
}
