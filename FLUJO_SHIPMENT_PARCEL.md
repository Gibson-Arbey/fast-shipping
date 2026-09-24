# Flujo Shipment y Parcel

## Responsabilidades

`Shipment` agrupa administrativamente paquetes de un mismo remitente. No guarda contadores ni representa una operación de entrega. Cada `Parcel` mantiene su propia dirección de destino, tracking UUID y estado. `ms-logistics` opera sobre `parcelId`; `Delivery` ya tenía esa relación y no requiere migración.

Shipping es la fuente de verdad de `Parcel.status`, `ParcelHistory` y del estado derivado de Shipment. Logistics conserva el ciclo operativo de Delivery y comunica cada cambio a Shipping por HTTP.

## Flujo

1. `POST /api/shipment` crea un Shipment en `CREATED` usando `senderAddressId`.
2. `POST /api/parcel` crea cada Parcel en `CREATED`, asigna su tracking UUID y guarda el historial inicial. Cada request puede tener un `destinationAddressId` distinto. Si falla el guardado del historial, el guardado local del Parcel se revierte.
3. `PATCH /api/parcel/{parcelId}/shipment/{shipmentId}` asocia el Parcel al Shipment. Un Parcel solo puede asociarse una vez y antes de ser asignado.
4. `POST /api/delivery` crea la Delivery de Logistics para el `parcelId`; su estado `PENDING` se comunica como Parcel `ASSIGNED`.
5. `PATCH /api/delivery/{id}/start` comunica Parcel `IN_TRANSIT`.
6. `PATCH /api/delivery/{id}/complete` comunica Parcel `DELIVERED`.
7. Cada cambio aceptado crea un `ParcelHistory` con `parcelId`, estado, hora, usuario del JWT, ubicación y observación. Se puede enviar `location` y `observation` como body opcional en las transiciones de Delivery; las llamadas existentes sin body siguen siendo válidas.
8. Shipping recalcula Shipment con sus Parcels después de cada cambio de estado.

Cada transición de Parcel es inmutable y valida el estado actual. Repetir el mismo estado no genera una segunda entrada de historial. Un Shipment sin Parcels permanece en `CREATED`. Persistir Parcel, historial y cambio de Shipment se hace en una transacción local de Shipping.

## Estados

| Parcel | Delivery de Logistics |
|---|---|
| `ASSIGNED` | `PENDING` |
| `IN_TRANSIT` | `IN_TRANSIT` |
| `DELIVERED` | `DELIVERED` |
| `DELIVERY_FAILED` | `FAILED` |
| `CANCELLED` | `CANCELLED` |

Se conservan `CONFIRMED`, `PICKED_UP`, `OUT_FOR_DELIVERY` y los demás valores preexistentes de `ParcelStatus`. Los estados homogéneos `CREATED`, `DELIVERED` y `CANCELLED` resuelven respectivamente a Shipment `CREATED`, `COMPLETED` y `CANCELLED`. Cualquier combinación distinta resuelve a `PROCESSING`; así se representan también los estados legacy o una mezcla de paquetes completados y pendientes, ya que Shipment no tiene estados `FAILED` ni parciales.

## APIs agregadas

- `POST /api/shipment`, `GET /api/shipment` y `GET /api/shipment/{id}`.
- `PATCH /api/parcel/{parcelId}/shipment/{shipmentId}`.
- `PATCH /api/parcel/{parcelId}/status` para actualizar el estado con auditoría.
- Los `PATCH` de transición de Delivery aceptan opcionalmente `{ "location": "...", "observation": "..." }`.

La integración logística usa `services.shipping.base-url` (`SHIPPING_SERVICE_BASE_URL`, por defecto `http://localhost:8082`) y reenvía el JWT validado de la solicitud para atribuir el historial al usuario autenticado.

## Persistencia y compatibilidad

La migración `V3__add_shipment_status.sql` agrega `ship_status` con default `CREATED` para datos existentes, repara tracking numbers vacíos y exige unicidad/no nulidad. `ms-logistics` no necesita migración: `deliveries.parc_id` ya guarda el Parcel y no hay `shipmentId` en Delivery.

La llamada entre servicios es síncrona y se realiza antes de guardar el nuevo estado local de Delivery. Si Shipping no responde, Logistics devuelve el error sin avanzar Delivery y se puede reintentar la misma operación. Cada base de datos conserva su transacción local; no se agregó outbox ni transacción distribuida. Si el guardado local falla después de actualizar Shipping, repetir la operación es seguro porque Shipping trata el estado actual como idempotente y no duplica el historial.
