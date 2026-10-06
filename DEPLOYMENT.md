# Ejecución con Docker Compose

1. Copia `.env.example` a `.env` y define credenciales propias antes de usarlo fuera del entorno local.
2. Inicia infraestructura, microservicios, Eureka y gateway con `docker compose up --build`.
3. Para incluir `ms-notification`, completa las credenciales SMTP y Twilio del `.env` y ejecuta `docker compose --profile notifications up --build`.

El gateway queda en `http://localhost:8080`, Eureka en `http://localhost:8761` y la consola RabbitMQ en `http://localhost:15672`. Las bases de datos se mantienen accesibles en los puertos 5432, 5433 y 5434.

Compose crea el usuario RabbitMQ `fastshipping` al inicializar un volumen nuevo. Si ya existe el volumen `rabbitmq_data` de una ejecución anterior con `guest`, créalo o actualiza su contraseña dentro del contenedor antes de iniciar las apps:

```powershell
docker compose up -d rabbitmq
docker compose exec rabbitmq rabbitmqctl add_user fastshipping fastshipping_dev
docker compose exec rabbitmq rabbitmqctl set_user_tags fastshipping management
docker compose exec rabbitmq rabbitmqctl set_permissions -p / fastshipping ".*" ".*" ".*"
```

Si el usuario ya existe, cambia `add_user` por:

```powershell
docker compose exec rabbitmq rabbitmqctl change_password fastshipping fastshipping_dev
```

Para cambiar las credenciales, usa los mismos valores de `RABBITMQ_USERNAME` y `RABBITMQ_PASSWORD` para RabbitMQ y las apps. `INTERNAL_SERVICE_API_KEY` debe coincidir en `ms-user` y `ms-shipping`; su valor incluido es solo para desarrollo local.

Cuando cambia el estado de un Parcel, Shipping consulta el correo del dueño de `destinationAddressId` en la ruta interna de `ms-user` y publica el evento `notification.email`. La publicación es secundaria al cambio de estado; si el servicio de contactos o RabbitMQ falla, Shipping registra el error y conserva el cambio. Un outbox transaccional queda pendiente para reintentar notificaciones con garantía ante fallos.
