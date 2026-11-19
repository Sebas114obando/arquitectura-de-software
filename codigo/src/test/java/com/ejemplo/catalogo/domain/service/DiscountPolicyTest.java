package com.ejemplo.catalogo.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para las reglas de negocio RN-1 y RN-2 (Hoja 1).
 * Variante Fila 3: Monitor ($600.000), u = 3, d = 12%.
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
    @DisplayName("RN-1: Cantidad igual al umbral u (u=3) recibe descuento d=12%")
    void conDescuentoEnUmbral() {
        int cantidad = 3; // u
        int pct = discountPolicy.calcularPorcentajeDescuento(cantidad);
        assertEquals(12, pct);

        long total = discountPolicy.calcularTotal(cantidad, PRECIO_UNITARIO, pct);
        assertEquals(1584000L, total); // RN-2
    }

    @Test
    @DisplayName("RN-1 y RN-2: Cantidad mayor al umbral en Hoja 1 recibe descuento d=12%")
    void conDescuentoSobreUmbralHoja1() {
        int cantidad = 19;
        int pct = discountPolicy.calcularPorcentajeDescuento(cantidad);
        assertEquals(12, pct);

        long total = discountPolicy.calcularTotal(cantidad, PRECIO_UNITARIO, pct);
        assertEquals(10032000L, total); // RN-2
    }
}
