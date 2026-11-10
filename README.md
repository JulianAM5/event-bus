# La Taberna del Bus de Eventos — "El Gólem Alquímico"

Sistema de una taberna universitaria desacoplado mediante un **Event Bus** (Java + Maven, sin dependencias externas).

## Estructura

```
pom.xml                                  # groupId uabc.julian, sin dependencias
src/main/java/uabc/julian/
├── App.java                             # Demostración del flujo completo
├── bus/                                 # EventBus y Suscriptor<T>
├── eventos/                             # Eventos inmutables (records)
├── componentes/                         # SistemaDePedidos, Barra, Cocina, Banda, SistemaDeSonido, PanelLED
└── extensiones/SistemaDeHumo.java       # Nuevo suscriptor, sin modificar código existente
docs/                                    # Diagramas PlantUML (clases, secuencia, componentes)
```

## Cómo ejecutar

Requiere Maven y el JDK que pide el `pom.xml` (`maven.compiler.release` = 27).
El proyecto no tiene dependencias externas.

```bash
mvn compile
java -Dfile.encoding=UTF-8 -cp target/classes uabc.julian.App
```

También se puede ejecutar directamente con Maven:

```bash
mvn compile exec:java -Dexec.mainClass=uabc.julian.App
```

La demo dura ~13 s: pedido de la mesa 5, arranca la banda, se instala el `SistemaDeHumo`
en caliente y entra un segundo pedido.

## Diseño

- **EventBus**: `suscribir(Class<T>, Suscriptor<T>)`, `desuscribir(...)`, `publicar(Evento)`.
  Seguro para hilos, aísla excepciones de suscriptores y entrega también a suscriptores de
  supertipos (`Evento.class` = escuchar todo).
- **Eventos**: `record`s inmutables (la lista de items se copia defensivamente).
- **Barra / Cocina**: suscriptores de `PedidoRealizadoEvent`; tras 2 s / 5 s publican
  `BebidaServidaEvent` / `ComidaPreparadaEvent`.
- **Banda**: publica `BandaTocandoEvent` cada 4 s.
- **Extensibilidad**: `SistemaDeHumo` reacciona a "Through the Fire and Flames" sin modificar
  `Banda` ni `EventBus` (ver `App`).

## Diagramas UML

Fuentes en `docs/*.puml`. Generar imágenes: `java -jar plantuml.jar -tpng docs/*.puml`
(o con la extensión de VS Code / plantuml.com). Inclúyelas junto al código en el PDF.
