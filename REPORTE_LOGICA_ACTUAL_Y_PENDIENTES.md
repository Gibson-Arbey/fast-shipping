# Reporte de lógica actual y definiciones pendientes

**Proyecto:** `fast-shipping`  
**Fecha de corte:** 2026-09-21  
**Alcance:** estado implementado en el repositorio, con énfasis en `ms-logistics` y su relación con los demás microservicios.

## 1. Resumen ejecutivo

El proyecto está organizado como una solución de microservicios Spring Boot con Clean Architecture/Hexagonal. Cada microservicio separa el dominio, los casos de uso, los adapters de infraestructura y los entry points HTTP.

Actualmente existen los siguientes bloques:

- `discovery-server`: registro de servicios mediante Eureka.
- `api-gateway`: punto de entrada HTTP, autenticación de borde y enrutamiento hacia los microservicios.
- `ms-user`: usuarios, roles, autenticación y direcciones.
- `ms-shipping`: creación de paquetes y consulta de historial de paquetes.
- `ms-logistics`: vehículos, conductores, categorías de licencia, rutas, asignaciones, entregas e incidentes.
- `ms-notification`: esqueleto de aplicación sin casos de uso ni endpoints funcionales.

El flujo logístico principal ya está implementado hasta la persistencia local de una entrega:

1. Se autentica un usuario y se obtiene un JWT.
2. Se registran o consultan recursos logísticos.
3. Se crea una ruta activa con paradas.
4. Se asignan un conductor y un vehículo disponibles a la ruta.
5. Se inicia, completa o cancela la asignación.
6. Se crean entregas vinculadas a un `parcelId`, una asignación y una parada.
7. Se cambia el estado de la entrega y opcionalmente se registran incidentes.

La principal discontinuidad actual es que `ms-logistics` guarda solamente el identificador del parcel y no existe todavía un contrato para actualizar el estado real en `ms-shipping`. Por eso el adapter de tracking actual es no-op.

## 2. Componentes y responsabilidades actuales

| Componente | Puerto configurado | Responsabilidad actual | Estado |
|---|---:|---|---|
| `discovery-server` | `8761` | Registro y descubrimiento Eureka | Funcional |
| `api-gateway` | `8080` | Enrutamiento, load balancer y propagación del JWT | Funcional |
| `ms-user` | `8081` | Usuarios, roles, autenticación y direcciones | Funcional en su alcance actual |
| `ms-shipping` | `8082` | Parcelas/paquetes e historial de estados | Parcial |
| `ms-logistics` | `8083` | Planeación y operación logística | Funcional en su alcance actual |
| `ms-notification` | `8080` | No tiene lógica de negocio ni API implementada | Esqueleto |

### 2.1 Persistencia local

Cada servicio mantiene su propia base de datos PostgreSQL, sin foreign keys entre microservicios:

- `ms-user`: `localhost:5432/user-db`.
- `ms-shipping`: `localhost:5433/shipping-db`.
- `ms-logistics`: `localhost:5434/logistics-db`.
- `ms-notification`: actualmente no tiene una persistencia de negocio definida.

Hibernate utiliza `ddl-auto: none` y la estructura se controla con Flyway.

Existe una colisión de configuración pendiente: `ms-notification` también declara el puerto `8080`, que ya utiliza `api-gateway`. Debe definirse un puerto propio antes de levantar ambos procesos simultáneamente.

## 3. Arquitectura interna

La estructura de los microservicios sigue esta dirección de dependencias:

```text
HTTP / REST
    ↓
Entry points (controllers, DTOs, mappers)
    ↓
Use cases (orquestación de aplicación)
    ↓
Domain (modelos, reglas y puertos)
    ↑
Driven adapters (JPA, seguridad, integraciones externas)
```

### Domain

Contiene modelos y reglas de negocio. No depende de Spring, JPA ni HTTP. Los repositorios y gateways se expresan como interfaces del dominio.

### Use cases

Orquestan validaciones, consultas a puertos, cambios de estado y persistencia. No conocen las entidades JPA ni los DTOs HTTP.

### Driven adapters

Implementan los puertos del dominio. En `ms-logistics` incluyen repositorios Spring Data JPA, mappers entre dominio y JPA y el adapter no-op de tracking.

### Entry points

Exponen endpoints REST, validan requests con Bean Validation, convierten DTOs a comandos y transforman las respuestas de dominio a DTOs.

## 4. Lógica actual de `ms-logistics`

### 4.1 Recursos existentes

#### Vehicle

Representa un vehículo con:

- tipo;
- placa;
- capacidad máxima de peso (`maxWeight`);
- capacidad máxima de volumen (`maxVolume`);
- estado operativo.

Estados disponibles:

```text
AVAILABLE, ASSIGNED, IN_TRANSIT, MAINTENANCE, OUT_OF_SERVICE
```

El nombre Java correcto es `maxWeight`. La columna SQL existente se conserva como `vehi_maxweight`. No se acepta el alias legado `maxWeigth`.

#### Driver

Representa el conductor relacionado con un usuario de `ms-user` mediante `userId`. Tiene número de licencia, categorías de licencia y estado.

Estados disponibles:

```text
AVAILABLE, ASSIGNED, DRIVING, ON_LEAVE, SUSPENDED, INACTIVE
```

Las categorías de licencia se almacenan dentro de `ms-logistics`. No se valida todavía que una categoría sea compatible con el tipo de vehículo.

#### LicenseCategory

Catálogo local de categorías de licencia. Se relaciona con conductores mediante la tabla interna `driver_license_categories`.

#### Route y RouteStop

Una ruta tiene nombre, estado y un conjunto de paradas ordenadas. El dominio exige como mínimo dos paradas y secuencias únicas.

Estados de ruta:

```text
ACTIVE, INACTIVE
```

#### RouteAssignment

Representa la asignación de una ruta a un conductor y un vehículo:

- `id`;
- `routeId`;
- `driverId`;
- `vehicleId`;
- `status`;
- `startedAt`;
- `completedAt`.

Estados:

```text
PLANNED, IN_PROGRESS, COMPLETED, CANCELLED
```

#### Delivery

Representa la entrega de un shipment en una parada de una asignación:

- `parcelId`;
- `routeAssignmentId`;
- `routeStopId`;
- `status`.

No contiene `shipmentId` y no existe actualmente una relación JPA o REST con `ms-shipping`.

Estados:

```text
PENDING, IN_TRANSIT, DELIVERED, FAILED, CANCELLED
```

#### Incident

Representa una incidencia operativa:

- referencia obligatoria a `routeAssignmentId`;
- referencia opcional a `deliveryId`;
- tipo;
- descripción;
- estado;
- fechas de creación y resolución;
- usuario reportante (`reportedBy`).

Tipos actuales:

```text
VEHICLE_BREAKDOWN
ACCIDENT
TRAFFIC_DELAY
PACKAGE_DAMAGED
ADDRESS_NOT_FOUND
DELIVERY_ATTEMPT_FAILED
OTHER
```

Estados:

```text
OPEN, IN_PROGRESS, RESOLVED, CANCELLED
```

### 4.2 Transiciones de recursos

Al crear una asignación se validan la existencia de la ruta, el conductor y el vehículo. Además:

- la ruta debe estar `ACTIVE`;
- el conductor debe estar `AVAILABLE`;
- el vehículo debe estar `AVAILABLE`.

Después de crear la asignación:

```text
Driver:  AVAILABLE → ASSIGNED
Vehicle: AVAILABLE → ASSIGNED
```

Al iniciar la asignación:

```text
RouteAssignment: PLANNED → IN_PROGRESS
Driver:           ASSIGNED → DRIVING
Vehicle:          ASSIGNED → IN_TRANSIT
```

Al completar la asignación:

```text
RouteAssignment: IN_PROGRESS → COMPLETED
Driver:           DRIVING → AVAILABLE
Vehicle:          IN_TRANSIT → AVAILABLE
```

Al cancelar antes de iniciar:

```text
RouteAssignment: PLANNED → CANCELLED
Driver:           ASSIGNED → AVAILABLE
Vehicle:          ASSIGNED → AVAILABLE
```

Una asignación iniciada no puede cancelarse con la lógica actual. Una asignación completada tampoco puede reabrirse.

### 4.3 Transiciones de entregas

```text
PENDING → IN_TRANSIT → DELIVERED
                    └→ FAILED

PENDING → CANCELLED
```

Reglas actuales:

- solo una entrega `PENDING` puede iniciar;
- solo una entrega `IN_TRANSIT` puede completarse o fallar;
- solo una entrega `PENDING` puede cancelarse;
- no se permite modificar una entrega finalizada.

Al crear una entrega:

- la asignación debe existir;
- la asignación debe estar `PLANNED` o `IN_PROGRESS`;
- la ruta de la asignación debe existir;
- la parada debe pertenecer a esa ruta.

La validación de pertenencia de la parada se realiza en el caso de uso. La base de datos mantiene foreign keys internas hacia `route_assignments` y `route_stops`, pero no una restricción compuesta que relacione directamente una parada con la ruta de la asignación.

### 4.4 Transiciones de incidentes

```text
OPEN → IN_PROGRESS → RESOLVED
                  └→ CANCELLED

OPEN → RESOLVED
OPEN → CANCELLED
```

Un incidente nuevo exige una asignación y un usuario reportante. El delivery es opcional, pero si se informa debe existir y pertenecer a la asignación indicada.

El usuario reportante se obtiene del principal autenticado. No existe foreign key hacia `ms-user` porque cada microservicio mantiene su propia base de datos.

### 4.5 Persistencia de `ms-logistics`

Las migraciones actuales son:

- `V1__create_vehicle.sql`: vehículos.
- `V2__create_driver_and_license_categories.sql`: conductores, categorías y relación conductor-categoría.
- `V3__create_route.sql`: rutas y paradas.
- `V4__create_route_assignments_deliveries_incidents.sql`: asignaciones, entregas e incidentes.

Las entidades de asignación, entrega e incidente guardan identificadores escalares. No tienen relaciones JPA con entidades de otros microservicios.

La migración sí crea foreign keys internas:

- asignación → ruta, conductor y vehículo;
- entrega → asignación y parada;
- incidente → asignación y entrega.

No crea foreign keys hacia shipments, parcels, usuarios, direcciones ni otras bases de datos.

## 5. API actual

Los endpoints de `ms-logistics` usan rutas singulares y versionado HTTP `1` mediante la configuración actual de Spring.

### Recursos base

| Recurso | Operaciones principales |
|---|---|
| `/api/vehicle` | `POST`, `GET`, `PATCH /{id}/status` |
| `/api/driver` | `POST`, `GET`, `GET /{id}`, `PATCH /{id}/status` |
| `/api/license-category` | `POST`, `GET`, `GET /{code}` |
| `/api/route` | `POST`, `GET`, `GET /{id}`, `PUT /{id}`, `PATCH /{id}/status` |

### Route assignments

| Método | Endpoint | Resultado |
|---|---|---|
| `POST` | `/api/route-assignment` | Crea una asignación `PLANNED` |
| `GET` | `/api/route-assignment` | Lista con filtros opcionales |
| `GET` | `/api/route-assignment/{id}` | Consulta una asignación |
| `PATCH` | `/api/route-assignment/{id}/start` | Inicia la asignación |
| `PATCH` | `/api/route-assignment/{id}/complete` | Completa la asignación |
| `PATCH` | `/api/route-assignment/{id}/cancel` | Cancela una asignación planificada |

Filtros disponibles: `routeId`, `driverId`, `vehicleId` y `status`.

### Deliveries

| Método | Endpoint | Resultado |
|---|---|---|
| `POST` | `/api/delivery` | Crea una entrega `PENDING` |
| `GET` | `/api/delivery` | Lista con filtros opcionales |
| `GET` | `/api/delivery/{id}` | Consulta una entrega |
| `PATCH` | `/api/delivery/{id}/start` | Pasa a `IN_TRANSIT` |
| `PATCH` | `/api/delivery/{id}/complete` | Pasa a `DELIVERED` |
| `PATCH` | `/api/delivery/{id}/fail` | Pasa a `FAILED` |
| `PATCH` | `/api/delivery/{id}/cancel` | Pasa a `CANCELLED` |

Filtros disponibles: `parcelId`, `routeAssignmentId`, `routeStopId` y `status`.

### Incidents

| Método | Endpoint | Resultado |
|---|---|---|
| `POST` | `/api/incident` | Crea un incidente `OPEN` |
| `GET` | `/api/incident` | Lista con filtros opcionales |
| `GET` | `/api/incident/{id}` | Consulta un incidente |
| `PATCH` | `/api/incident/{id}/start` | Pasa a `IN_PROGRESS` |
| `PATCH` | `/api/incident/{id}/resolve` | Pasa a `RESOLVED` |
| `PATCH` | `/api/incident/{id}/cancel` | Pasa a `CANCELLED` |

Filtros disponibles: `routeAssignmentId`, `deliveryId`, `type` y `status`.

Las operaciones de creación responden `201`. Las consultas y transiciones responden `200`. Las validaciones y errores de dominio se representan mediante `ProblemDetail`.

## 6. Flujo actual entre microservicios

### 6.1 Autenticación

1. El cliente invoca `POST /api/auth/login` a través del gateway.
2. `ms-user` valida email y contraseña.
3. `ms-user` busca el usuario y su rol.
4. `ms-user` genera un JWT con `userId`, email y rol.
5. El cliente envía el JWT en `Authorization: Bearer ...` en las siguientes solicitudes.
6. El gateway conserva y reenvía el header `Authorization`.
7. Cada microservicio valida el JWT localmente con la configuración compartida.

La mayoría de endpoints requieren autenticación. En `ms-user` existe una regla explícita para restringir la creación de usuarios a `ADMIN`; en `ms-logistics` no hay todavía autorización por operación o rol.

### 6.2 Creación de un paquete

`ms-shipping` permite crear un Parcel con:

- dirección de destino;
- dimensiones y peso;
- tipo;
- descripción.

El paquete inicia en estado `CREATED`, genera un tracking number y registra un `ParcelHistory` inicial con el usuario autenticado.

Actualmente `CreateShipmentUseCase` está vacío. Por lo tanto, el flujo completo de shipment todavía no está implementado en `ms-shipping`.

### 6.3 Conexión entre shipping y logistics

La conexión actual es únicamente conceptual mediante `parcelId`:

```text
ms-logistics.deliveries.parc_id
        ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
ms-shipping.parcels.parc_id
```

No existe actualmente:

- cliente HTTP entre ambos servicios;
- evento de dominio o mensajería;
- endpoint de `ms-shipping` para actualizar Parcel/Shipment;
- validación remota de que el shipment existe;
- actualización real del estado del paquete cuando cambia una Delivery.

Los casos de uso de Delivery invocan `DeliveryTrackingGateway`, pero su implementación actual (`NoOpDeliveryTrackingAdapter`) no realiza ninguna acción externa.

## 7. Definiciones pendientes

### Prioridad alta: contratos de negocio

#### 7.1 Shipment, Parcel y Delivery

Debe definirse con precisión:

- si una Delivery representa un shipment completo o un parcel individual;
- si un shipment puede contener varios parcels en una misma ruta;
- si el parcel es la unidad operativa correcta o si se debe relacionar también el shipment;
- cuándo nace un shipment y quién lo asocia a los parcels;
- qué servicio es la fuente de verdad de cada estado;
- qué ocurre si el shipment no existe al crear la Delivery.

La implementación actual usa `parcelId`, pero el modelo de `ms-shipping` todavía no expone el flujo completo de shipments ni un contrato de consulta/validación para ese parcel.

#### 7.2 Contrato de tracking

Antes de reemplazar el adapter no-op debe definirse:

- REST síncrono, eventos o mensajería;
- endpoint o tópico y formato del mensaje;
- identificador de correlación (`parcelId`, `shipmentId` o tracking number);
- equivalencia entre estados de Delivery y estados de Parcel/Shipment;
- comportamiento ante reintentos, duplicados y mensajes fuera de orden;
- autenticación entre microservicios;
- timeout, retry, circuit breaker y estrategia ante indisponibilidad;
- si la actualización de tracking es obligatoria o eventual.

#### 7.3 Reglas de asignación

Falta decidir:

- si un conductor puede tener varias asignaciones planificadas simultáneas;
- si un vehículo puede reservarse para varias rutas futuras;
- si una ruta puede tener más de una asignación activa;
- qué hacer con una asignación anterior no cerrada;
- si se permite editar una ruta que ya tiene asignaciones o entregas;
- si cancelar una asignación debe cancelar automáticamente sus entregas pendientes;
- si completar una asignación exige que todas sus entregas estén entregadas, fallidas o canceladas;
- qué sucede con los recursos si una escritura parcial falla.

Hoy la disponibilidad se valida en el caso de uso, pero no existe una regla de unicidad para evitar carreras concurrentes.

#### 7.4 Compatibilidad de licencia y vehículo

El dominio tiene categorías de licencia para el conductor y tipos de vehículo, pero no define una tabla o regla normativa que permita compararlos. Se debe definir:

- qué categorías habilitan cada tipo de vehículo;
- si la compatibilidad depende del peso o volumen;
- si existen excepciones por país, ciudad o tipo de operación;
- quién mantiene esa configuración.

Hasta definirlo, el sistema no aplica esta validación.

#### 7.5 Incidentes

Debe definirse:

- catálogo final de tipos y posibilidad de configuración dinámica;
- severidad e impacto;
- quién puede iniciar, resolver o cancelar un incidente;
- si resolver un incidente requiere observación, evidencia o ubicación;
- si un incidente bloquea la ruta, el vehículo, el conductor o una entrega;
- si genera notificaciones o cambios automáticos de estado;
- tiempos objetivo de atención y auditoría.

### Prioridad media: seguridad y API

#### 7.6 Autorización por rol

La autenticación JWT está implementada, pero `ms-logistics` actualmente acepta cualquier usuario autenticado para sus operaciones. Debe definirse una matriz como:

| Operación | ADMIN | DISPATCHER | DRIVER | CUSTOMER |
|---|---:|---:|---:|---:|
| Crear vehículo/conductor/ruta | ? | ? | ? | ? |
| Crear asignación | ? | ? | ? | ? |
| Iniciar asignación | ? | ? | ? | ? |
| Crear o actualizar entrega | ? | ? | ? | ? |
| Registrar incidente | ? | ? | ? | ? |
| Resolver incidente | ? | ? | ? | ? |

También debe definirse si el `reportedBy` siempre debe ser el usuario autenticado o si un operador puede reportar en nombre de otro usuario.

#### 7.7 Contrato de errores

Los microservicios usan `ProblemDetail`, pero aún conviene cerrar un contrato común para:

- códigos de error estables;
- estructura de errores de validación con todos los campos inválidos;
- correlación de solicitudes;
- mensajes para cliente versus mensajes técnicos;
- respuestas `401`, `403`, `404`, `409` y `500` homogéneas.

#### 7.8 Paginación y ordenamiento

Los listados actuales retornan `List` completa. Debe definirse cuándo se requerirán:

- paginación;
- ordenamiento;
- filtros por rango de fechas;
- límites máximos de consulta;
- exportación o reportes.

### Prioridad media: consistencia y operación

#### 7.9 Transacciones y concurrencia

Crear una asignación cambia tres agregados/repositorios: asignación, conductor y vehículo. Iniciar, completar y cancelar también actualizan varios recursos.

Actualmente esas operaciones no tienen una transacción distribuida y la decisión de consistencia ante una falla intermedia todavía no está definida. Se debe decidir:

- si cada microservicio opera con transacciones locales;
- si se requiere outbox/eventos;
- cómo se recupera una actualización parcial;
- si se necesitan locks o restricciones únicas para reservar conductor y vehículo;
- qué comportamiento debe observar el cliente durante una carrera concurrente.

No se implementó Saga ni transacción distribuida, de acuerdo con el alcance actual.

#### 7.10 Fechas y zona horaria

El dominio usa `LocalDateTime` y algunos casos de uso llaman directamente a `LocalDateTime.now()`. Debe definirse:

- si todo el sistema trabaja en UTC;
- formato JSON oficial;
- zona horaria de negocio;
- fuente de tiempo para pruebas;
- auditoría de creación, inicio, finalización y resolución.

#### 7.11 Observabilidad

Eureka, health checks y Prometheus están configurados parcialmente. Falta definir:

- identificador de correlación entre gateway y microservicios;
- trazabilidad de una entrega de extremo a extremo;
- métricas de asignaciones, entregas e incidentes;
- logs estructurados;
- alertas y dashboards;
- exportación de trazas en todos los servicios.

### Prioridad alta técnica: servicios incompletos

#### 7.12 `ms-shipping`

El servicio crea Parcel y su historial inicial, pero aún falta cerrar:

- creación y ciclo de vida de Shipment;
- endpoints de consulta de parcels y shipments;
- transición de estados de Parcel;
- contrato para recibir actualizaciones desde logística;
- idempotencia de actualizaciones;
- validación de dirección contra `ms-user` o contrato de referencias.

#### 7.13 `ms-notification`

Actualmente solo contiene la aplicación base y configuración. Falta decidir e implementar:

- canales soportados: email, SMS, push u otros;
- plantillas y destinatarios;
- proveedor externo;
- eventos que generan notificaciones;
- reintentos y dead-letter queue;
- persistencia del estado de envío;
- endpoint o consumo de eventos;
- puerto distinto de `8080`.

## 8. Decisiones explícitas que ya están tomadas

Las siguientes decisiones forman parte del estado actual y no deben reinterpretarse como funcionalidades faltantes:

- Se mantienen URLs singulares.
- `ms-logistics` usa el puerto `8083`.
- Delivery usa `parcelId` como referencia externa en el estado actual del código.
- No hay foreign keys hacia bases de otros microservicios.
- No se valida compatibilidad licencia/vehículo hasta tener la regla normativa.
- El tracking real queda pendiente de un contrato con `ms-shipping`.
- El adapter de tracking actual es no-op.
- No se implementan GPS, optimización de rutas, Kafka, eventos, Saga ni transacciones distribuidas.
- Los listados continúan retornando `List`, sin paginación.

## 9. Recomendación de siguiente orden de trabajo

1. Definir el modelo y ciclo de vida de `Shipment`, `Parcel` y `Delivery`.
2. Definir e implementar el contrato de tracking entre `ms-logistics` y `ms-shipping`.
3. Completar el ciclo de vida de Shipment/Parcel en `ms-shipping`.
4. Definir autorización por rol para cada endpoint logístico.
5. Cerrar reglas de asignación, cancelación, duplicados y finalización.
6. Resolver transacciones locales, concurrencia e idempotencia.
7. Definir el alcance real de `ms-notification` y corregir su puerto.
8. Agregar pruebas de integración contra PostgreSQL y pruebas de contrato entre servicios.
9. Definir observabilidad, auditoría y operación en los distintos ambientes.

## 10. Evidencia técnica de la implementación actual

Archivos de referencia principales:

- `ms-logistics/README.md`: decisiones de logística documentadas.
- `ms-logistics/domain/model`: modelos y transiciones de dominio.
- `ms-logistics/domain/usecase`: casos de uso y validaciones de aplicación.
- `ms-logistics/infrastructure/driven-adapters/jpa-repository`: persistencia y adapters.
- `ms-logistics/infrastructure/entry-points/api-rest`: controllers, DTOs y manejo de errores.
- `ms-logistics/applications/app-service/src/main/resources/db/migration/V4__create_route_assignments_deliveries_incidents.sql`: estructura de asignaciones, entregas e incidentes.
- `api-gateway/src/main/java/co/fastshipping/api_gateway/config/GatewayConfig.java`: rutas y load balancing.
- `ms-shipping/domain/usecase`: estado actual del flujo de paquetes y shipments.
- `ms-notification/applications/app-service`: estado actual del servicio de notificaciones.

La compilación y las pruebas actuales de `ms-logistics` y `api-gateway` se encuentran verdes según la última verificación del repositorio. La migración V4 todavía requiere validarse contra una instancia PostgreSQL real en el ambiente de ejecución.
