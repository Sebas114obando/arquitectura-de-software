# ADR-0001 · Elección de arquitectura hexagonal para modificabilidad de reglas comerciales

Variante: Q1 / fila 3

- Fecha: 2026-11-18 20:30
- Estado: aceptada

## Contexto
La tienda comercializa un único producto, Monitor (fila 3: precio unitario $600.000, stock inicial de 24 unidades y umbral inicial de volumen $u = 3$ con descuento base $d = 12\%$). El atributo de calidad prioritario asignado es Q1 (Modificabilidad de las reglas de descuento): el equipo comercial actualiza las reglas de descuento frecuentemente. Se requiere una arquitectura donde alterar las políticas de liquidación sea económico, directo y esté estrictamente aislado del flujo transaccional de pedidos, del control de inventario y de los mecanismos de entrega (HTTP y persistencia).

## Alternativas consideradas
- Arquitectura por capas (`catalogo`) — descartada porque en la estructura por capas tradicional, la lógica de descuento y cálculo de totales ($RN-1$ y $RN-2$) se aloja típicamente dentro de `ProductService` o acoplada a las entidades transaccionales. Al incorporar nuevos tramos de descuento sobre el monitor (donde cantidades $\ge 3u = 9$ reciben tarifa preferencial), se obligaría a intervenir el mismo servicio que gestiona transacciones y base de datos, incrementando el riesgo de regresiones sobre el control del stock crítico de 24 unidades.
- Arquitectura monilito modular (`tienda`) — descartada porque la partición en módulos gruesos (`catalogo` y `pedidos`) añade fricción innecesaria para un único producto y stock reducido. En dicho diseño, las políticas de descuento residen dentro del módulo de pedidos acopladas a la entidad `Order`, impidiendo tratar el algoritmo de precios como una política pura de dominio independiente del ciclo de vida del pedido.
- Arquitectura orientada a eventos (`tienda`) — descartada porque introduce consistencia eventual y asincronía mediante broker de mensajería. En una tienda con stock limitado a 24 monitores donde la línea 3 de la muestra intenta pedir 20 unidades y debe ser rechazada de forma inmediata y determinista sin consumir stock, la asincronía de eventos dificulta la validación síncrona de inventario y sobrecarga el sistema con manejo de idempotencia sin aportar beneficio a la modificabilidad de las fórmulas de descuento.

## Decisión
Se decide implementar la **Arquitectura Hexagonal (Puertos y Adaptadores)** y adoptar **H2 en memoria** para la persistencia. La lógica de negocio y las reglas de descuento se confinan en el núcleo (`domain`), aisladas de frameworks externos (Spring Boot) y de tecnologías de infraestructura (JPA/Web) mediante puertos explícitos (`domain/port`) y adaptadores (`adapter`).

## Consecuencias
**Se gana:** Desacoplamiento total de la regla de descuento y cálculo de totales en `DiscountPolicy` (dominio puro), permitiendo modificar o añadir tramos comerciales con costo mínimo y sin tocar adaptadores web ni de base de datos. La verificación de reglas de dependencias es limpia y auditable (`ArchitectureRuleTest`). H2 en memoria proporciona arranques limpios reproducibles sin dependencias externas.
**Se pierde:** Mayor volumen de código ceremonial y clases de indirección para una tienda de un solo producto (separación entre modelos de dominio y entidades JPA, mappers, puertos de entrada y salida, y adaptadores independientes de persistencia y web).
**Predicción del costo de la Hoja 2:** Para absorber el cambio de la Hoja 2 (tramo adicional de descuento de $d + 5 = 17\%$ para cantidad $\ge 3u = 9$ unidades):
- Esperamos modificar exactamente una clase de producción del dominio: `DiscountPolicy.java` (paquete `com.ejemplo.catalogo.domain.service`), ajustando la escala de descuento.
- ¿Se espera tocar la clase que calcula el total? Sí, `DiscountPolicy.java` calcula el total y descuento, y es la única clase de lógica que se modificará.
- ¿Se espera tocar la clase que registra el pedido? No, `PedidoUseCaseService.java` delega el cálculo a `DiscountPolicy` y no requerirá cambios.
- ¿Se espera tocar las pruebas de la Hoja 1? Sí, se modificará `DiscountPolicyTest.java` para reflejar el nuevo porcentaje del 17% en cantidades $\ge 9$, mientras que las pruebas de casos de uso (`PedidoUseCaseServiceTest.java`) y de arquitectura no se tocarán.
- No se crearán nuevas clases de producción; únicamente se creará la clase de prueba de la muestra de Hoja 2 (`MuestraHoja2Test.java`).
