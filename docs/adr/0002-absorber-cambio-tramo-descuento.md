# ADR-0002 · Absorción del nuevo tramo de descuento comercial

Variante: Q1 / fila 3

- Fecha: 2026-11-18 23:45
- Estado: aceptada

## Contexto
El equipo comercial actualizó las políticas de descuento de la tienda (Hoja 2 - Q1): para pedidos cuya cantidad de monitores sea igual o superior a $3u = 9$ unidades, se aplica un descuento preferencial de $d + 5 = 17\%$ (5 puntos porcentuales sobre el descuento base del 12%). Para volúmenes entre 3 y 8 unidades (de $u$ a $3u - 1$) se conserva el descuento del 12%, y para menos de 3 unidades sigue sin otorgarse descuento. Se requiere absorber este cambio en la arquitectura hexagonal elegida evaluando el costo real frente a la predicción del ADR-0001.

## Alternativas consideradas
- Incorporar la lógica del tramo superior en el servicio de aplicación `PedidoUseCaseService` — descartada porque rompería la separación de capas de la arquitectura hexagonal, trasladando reglas puras de dominio al servicio de aplicación y acoplando el cálculo al flujo de persistencia del monitor.
- Implementar un motor o patrón Strategy con clases independientes por tramo (`TramoBaseStrategy`, `TramoSuperiorStrategy`) — descartada por sobreabstracción innecesaria, dado que añadir complejidad arquitectónica para una regla de tres tramos fijos encarece el mantenimiento sin aportar valor al negocio.

## Decisión
Se decidió implementar el nuevo tramo condicional exclusivamente dentro del servicio de dominio `DiscountPolicy.java` en su método `calcularPorcentajeDescuento`. Ninguna clase de la capa de aplicación (`PedidoUseCaseService`) ni de los adaptadores de entrada o salida fue intervenida.

## Consecuencias
**Se gana:** Alta modificabilidad y costo de cambio localizado. La regla de negocio se absorbió en una única clase de producción de dominio (`DiscountPolicy.java`), manteniendo los adaptadores web, JPA y casos de uso 100% aislados e inalterados.
**Se pierde:** Se requirió actualizar las aserciones de `DiscountPolicyTest.java` y `MuestraHoja1Test.java` debido a que la línea 4 de la muestra (19 unidades) superaba el umbral de 9 y cambió su liquidación de $10.032.000 a $9.462.000.
**Veredicto sobre lo que se perdería y sobre la predicción:**
- Sobre lo que se perdería: La pérdida por sobrecarga ceremonial y clases de indirección advertida en el ADR-0001 no se manifestó en la absorción del cambio de la Hoja 2 (que resultó sumamente económica al tocar una sola clase), sino que se evidenció con total claridad durante la construcción inicial de la Hoja 1, donde estructurar una tienda para un único producto obligó a crear 23 archivos en el commit `d5c60f0` (puertos in/out, entidades JPA, adaptadores y mappers).
- Sobre la predicción: Acertó plenamente en cada elemento pronosticado:
  1. Modificar la clase que calcula el total: Acertó (se modificó `DiscountPolicy.java`).
  2. No tocar la clase que registra el pedido: Acertó (`PedidoUseCaseService.java` quedó intacta).
  3. Tocar pruebas de la Hoja 1: Acertó (se actualizó `DiscountPolicyTest.java` y `MuestraHoja1Test.java`).
  4. Cero clases de producción creadas: Acertó (solo se creó `MuestraHoja2Test.java` en pruebas).
