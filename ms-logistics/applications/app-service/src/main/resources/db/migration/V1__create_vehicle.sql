CREATE TABLE vehicles (
    vehi_id BIGSERIAL PRIMARY KEY,
    vehi_status VARCHAR(20) NOT NULL,
    vehi_type VARCHAR(20) NOT NULL,
    vehi_maxweight NUMERIC(10,2) NOT NULL CHECK (vehi_maxweight > 0),
    vehi_maxvolume NUMERIC(10,2) NOT NULL CHECK (vehi_maxvolume > 0),
    vehi_plate VARCHAR(20) NOT NULL UNIQUE
);