-- ============================================
-- Crear tabla shipments
-- ============================================

CREATE TABLE shipments (
    ship_id BIGSERIAL PRIMARY KEY,
    ship_status VARCHAR(50) NOT NULL,
    ship_originaddress VARCHAR(255) NOT NULL,
    ship_destinationaddress VARCHAR(255) NOT NULL,
    ship_createdat TIMESTAMP NOT NULL,
    ship_deliveredat TIMESTAMP
);


-- ============================================
-- Crear tabla shipmenthistories
-- ============================================

CREATE TABLE shipmenthistories (
    shhi_id BIGSERIAL PRIMARY KEY,
    ship_id BIGINT NOT NULL,
    shhi_status VARCHAR(50) NOT NULL,
    shhi_createdat TIMESTAMP NOT NULL,
    user_id BIGINT,
    shhi_location VARCHAR(255),
    shhi_observation VARCHAR(500),
    CONSTRAINT fk_shipmenthistory_shipment
        FOREIGN KEY (ship_id)
        REFERENCES shipments (ship_id)
);


-- ============================================
-- Agregar relación Parcel -> Shipment
-- ============================================

ALTER TABLE parcels
ADD COLUMN ship_id BIGINT;

ALTER TABLE parcels
RENAME COLUMN user_id TO addr_id;

ALTER TABLE parcels
ADD CONSTRAINT fk_parcel_shipment
    FOREIGN KEY (ship_id)
    REFERENCES shipments (ship_id);