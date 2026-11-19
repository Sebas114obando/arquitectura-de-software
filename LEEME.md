# Proyecto de Arquitectura de Software Empresarial

**Integrantes:** Cristina Buitrago y Juan Sebastián Obando  
**Variante:** Q1 / Fila 3 (Monitor, $600.000, stock 24, u = 3, d = 12%)  
**Arquitectura elegida:** Arquitectura Hexagonal con persistencia H2 en memoria.

---

## Comando para correr la muestra de la Hoja 1
Para ejecutar sobre la etiqueta `hoja-1` desde un arranque limpio:

```cmd
cd codigo
.\mvnw.cmd test -Dtest=MuestraHoja1Test -q
```

*(Líneas impresas: 4 pedidos con totales 1200000, 1584000, 0 y 10032000).*

---

## Comando para correr la muestra de la Hoja 2
Para ejecutar sobre la etiqueta `final` desde un arranque limpio:

```cmd
cd codigo
.\mvnw.cmd test -Dtest=MuestraHoja2Test -q
```

*(Líneas impresas: 3 pedidos con totales 4482000, 4224000 y 600000).*

---

## Comando para verificar la regla de arquitectura
```cmd
cd codigo
.\mvnw.cmd test -Dtest=ArchitectureRuleTest -q
```
