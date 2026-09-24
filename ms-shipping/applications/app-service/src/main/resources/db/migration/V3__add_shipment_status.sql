ALTER TABLE shipments
    ADD COLUMN ship_status VARCHAR(20) NOT NULL DEFAULT 'CREATED'
        CHECK (ship_status IN ('CREATED', 'PROCESSING', 'COMPLETED', 'CANCELLED'));

UPDATE parcels
SET parc_trackingnumber = gen_random_uuid()
WHERE parc_trackingnumber IS NULL;

ALTER TABLE parcels
    ALTER COLUMN parc_trackingnumber SET NOT NULL;

CREATE UNIQUE INDEX parcels_tracking_number_uq ON parcels (parc_trackingnumber);
