package com.ejemplo.catalogo.domain.service;

/**
 * Servicio de dominio puro para las reglas de descuento y total (RN-1 y RN-2).
 * Aislado de Spring, JPA y de los adaptadores de entrada y salida.
 *
 * Configuración para Fila 3:
 * - Umbral de volumen u = 3 unidades
 * - Descuento base d = 12%
 */
public class DiscountPolicy {

    public static final int UMBRAL_VOLUMEN = 3;
    public static final int DESCUENTO_BASE_PORCENTAJE = 12;

    /**
     * RN-1: Descuento por volumen.
     * Si cantidad >= u (3), recibe 12% de descuento; si no, 0%.
     */
    public int calcularPorcentajeDescuento(int cantidad) {
        if (cantidad >= UMBRAL_VOLUMEN) {
            return DESCUENTO_BASE_PORCENTAJE;
        }
        return 0;
    }

    /**
     * RN-2: Total del pedido en pesos enteros.
     * total = cantidad * precio * (100 - descuento) / 100
     */
    public long calcularTotal(int cantidad, long precioUnitario, int porcentajeDescuento) {
        return (long) cantidad * precioUnitario * (100 - porcentajeDescuento) / 100;
    }
}
