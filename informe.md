# Informe de Medición y Evaluación Arquitectónica

Variante: Q1 / fila 3

## Tabla de medición (git diff --name-status hoja-1 final)

| Medida | Resultado |
| :--- | :--- |
| Archivos creados | 1 (`codigo/src/test/java/com/ejemplo/catalogo/MuestraHoja2Test.java`) |
| Archivos modificados | 3 (`DiscountPolicy.java`, `DiscountPolicyTest.java`, `MuestraHoja1Test.java`) |
| ¿Se modificó el archivo o la clase que calcula el total? | Sí (`DiscountPolicy.java`) |
| ¿Se modificó el archivo o la clase que registra el pedido? | No (`PedidoUseCaseService.java` permaneció intacto) |
| ¿Se tocó una prueba de la Hoja 1? | Sí (`DiscountPolicyTest.java` y `MuestraHoja1Test.java`) |

## Respuestas a las preguntas de evaluación

### Primera: predicción contra medición
Al contrastar la predicción del ADR-0001 con la tabla medida con git diff, se comprueba un acierto pleno en las previsiones arquitectónicas. Se predijo con exactitud que se modificaría la clase responsable de liquidar el descuento y el total (`DiscountPolicy.java`), mientras que la clase de aplicación que registra los pedidos (`PedidoUseCaseService.java`) permanecería intacta. Asimismo, tal como pronosticó el ADR-0001, no se creó ninguna clase nueva en producción y únicamente se introdujo un archivo de prueba para la muestra de la Hoja 2 (`MuestraHoja2Test.java`), reflejado como el único archivo creado en la tabla. Finalmente, la predicción acertó al anticipar que se tocarían las pruebas de la Hoja 1 (`DiscountPolicyTest.java` y `MuestraHoja1Test.java`), pues el nuevo tramo porcentual alteró las aserciones sobre volúmenes iguales o superiores a nueve unidades.

### Segunda: el límite
Un cambio plausible donde la arquitectura hexagonal resultaría inferior a una arquitectura orientada a eventos sería la necesidad de reaccionar tras confirmar un pedido de monitor, emitiendo una factura fiscal, notificando al cliente y actualizando analíticas en tiempo real. En nuestra arquitectura sincrónica, agregar estas reacciones obligaría a modificar continuamente el caso de uso `PedidoUseCaseService` para coordinar puertos de salida adicionales en una misma transacción, incrementando acoplamiento y latencia. En contraste, eventos resolvería esta necesidad mediante la publicación de un evento de integración, permitiendo sumar múltiples consumidores sin tocar al productor, ventaja resaltada expresamente en su guía oficial para flujos extensibles.

### Tercera: lo que la regla no ve
Para la verificación en evidencia.txt se indujo intencionalmente la importación de `org.springframework.stereotype.Component` en la entidad pura `Product.java`, y la prueba `ArchitectureRuleTest` la detectó inmediatamente provocando el fallo de la ejecución. Sin embargo, existe una violación arquitectónica que esta regla no detectaría: la fuga de estructuras de persistencia o tipos técnicos dentro de métodos del dominio mediante clases no restringidas, como recibir un mapa genérico de base de datos sin un import prohibido. Tampoco detectaría si una clase de aplicación evade los puertos y accede al adaptador de persistencia mediante reflexión dinámica en tiempo de ejecución, pues el análisis evalúa únicamente los imports estáticos declarados en el archivo fuente.
