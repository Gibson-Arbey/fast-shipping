CREATE TABLE routes (
    rout_id BIGSERIAL PRIMARY KEY,
    rout_name VARCHAR(100) NOT NULL,
    rout_status VARCHAR(20) NOT NULL,
    CONSTRAINT routes_name_ukey UNIQUE (rout_name),
    CONSTRAINT routes_status_check CHECK (
        rout_status IN ('ACTIVE', 'INACTIVE')
    )
);

CREATE UNIQUE INDEX routes_name_lower_ukey
    ON routes (LOWER(rout_name));

CREATE TABLE route_stops (
    rost_id BIGSERIAL PRIMARY KEY,
    rost_route_id BIGINT NOT NULL,
    rost_sequence INTEGER NOT NULL,
    rost_city VARCHAR(100) NOT NULL,
    CONSTRAINT route_stops_sequence_check CHECK (rost_sequence > 0),
    CONSTRAINT route_stops_route_sequence_ukey UNIQUE (rost_route_id, rost_sequence),
    CONSTRAINT route_stops_route_fkey
        FOREIGN KEY (rost_route_id)
        REFERENCES routes (rout_id)
        ON DELETE CASCADE
);

CREATE INDEX route_stops_route_idx
    ON route_stops (rost_route_id);
