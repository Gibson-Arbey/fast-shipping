CREATE TABLE shipments (
    ship_id BIGSERIAL PRIMARY KEY,
    addr_id BIGINT NOT NULL,
    ship_createdat TIMESTAMP NOT NULL
);

CREATE TABLE parcelhistories (
    parh_id BIGSERIAL PRIMARY KEY,
    parc_id BIGINT NOT NULL,
    parh_status VARCHAR(30) NOT NULL
        CHECK (
            parh_status IN (
                'CREATED',
                'CONFIRMED',
                'ASSIGNED',
                'PICKED_UP',
                'IN_TRANSIT',
                'OUT_FOR_DELIVERY',
                'DELIVERED',
                'DELIVERY_FAILED',
                'CANCELLED'
            )
        ),
    parh_createdat TIMESTAMP NOT NULL,
    user_id BIGINT NOT NULL,
    parh_location VARCHAR(255),
    parh_observation VARCHAR(500),
    CONSTRAINT fk_parcelhistory_parcel
        FOREIGN KEY (parc_id)
        REFERENCES parcels (parc_id)
);

ALTER TABLE parcels
    ADD COLUMN parc_status VARCHAR(30) NOT NULL DEFAULT 'CREATED'
        CHECK (
            parc_status IN (
                'CREATED',
                'CONFIRMED',
                'ASSIGNED',
                'PICKED_UP',
                'IN_TRANSIT',
                'OUT_FOR_DELIVERY',
                'DELIVERED',
                'DELIVERY_FAILED',
                'CANCELLED'
            )
        ),
    ADD COLUMN ship_id BIGINT;

ALTER TABLE parcels
    RENAME COLUMN user_id TO addr_id;

ALTER TABLE parcels
    ADD CONSTRAINT fk_parcel_shipment
        FOREIGN KEY (ship_id)
        REFERENCES shipments (ship_id);
