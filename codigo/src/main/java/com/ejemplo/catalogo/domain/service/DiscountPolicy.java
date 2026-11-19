package com.ejemplo.catalogo.domain.service;

/**
 * Servicio de dominio puro para las reglas de descuento y total (RN-1 y RN-2).
 * Modificado para absorber el cambio de la Hoja 2 (Q1):
 * - Si cantidad >= 3u (9 unidades), descuento = d + 5 = 17%.
 * - Si 3u > cantidad >= u (3 a 8 unidades), descuento = d = 12%.
 * - Si cantidad < u (< 3 unidades), sin descuento = 0%.
 */
public class DiscountPolicy {

    public static final int UMBRAL_VOLUMEN = 3;
    public static final int DESCUENTO_BASE_PORCENTAJE = 12;
    public static final int DESCUENTO_TRAMO_SUPERIOR_PORCENTAJE = 17; // d + 5 puntos porcentuales

    /**
     * RN-1 actualizada con el tramo superior de Hoja 2.
     */
    public int calcularPorcentajeDescuento(int cantidad) {
        if (cantidad >= 3 * UMBRAL_VOLUMEN) {
            return DESCUENTO_TRAMO_SUPERIOR_PORCENTAJE;
        } else if (cantidad >= UMBRAL_VOLUMEN) {
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
