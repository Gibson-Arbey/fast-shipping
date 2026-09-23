CREATE TABLE route_assignments (
    roas_id BIGSERIAL PRIMARY KEY,
    rout_id BIGINT NOT NULL,
    driv_id BIGINT NOT NULL,
    vehi_id BIGINT NOT NULL,
    roas_status VARCHAR(20) NOT NULL,
    roas_startedat TIMESTAMP,
    roas_completedat TIMESTAMP,
    CONSTRAINT route_assignments_status_check CHECK (
        roas_status IN ('PLANNED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')
    ),
    CONSTRAINT route_assignments_dates_check CHECK (
        roas_completedat IS NULL OR roas_startedat IS NOT NULL
    ),
    CONSTRAINT route_assignments_completed_after_started_check CHECK (
        roas_completedat IS NULL OR roas_completedat >= roas_startedat
    ),
    CONSTRAINT route_assignments_route_fkey
        FOREIGN KEY (rout_id) REFERENCES routes (rout_id) ON DELETE RESTRICT,
    CONSTRAINT route_assignments_driver_fkey
        FOREIGN KEY (driv_id) REFERENCES drivers (driv_id) ON DELETE RESTRICT,
    CONSTRAINT route_assignments_vehicle_fkey
        FOREIGN KEY (vehi_id) REFERENCES vehicles (vehi_id) ON DELETE RESTRICT
);

CREATE INDEX route_assignments_route_idx ON route_assignments (rout_id);
CREATE INDEX route_assignments_driver_idx ON route_assignments (driv_id);
CREATE INDEX route_assignments_vehicle_idx ON route_assignments (vehi_id);
CREATE INDEX route_assignments_status_idx ON route_assignments (roas_status);

CREATE TABLE deliveries (
    deli_id BIGSERIAL PRIMARY KEY,
    parc_id BIGINT NOT NULL,
    rout_assignmentid BIGINT NOT NULL,
    rout_stopid BIGINT NOT NULL,
    deli_status VARCHAR(20) NOT NULL,
    CONSTRAINT deliveries_status_check CHECK (
        deli_status IN ('PENDING', 'IN_TRANSIT', 'DELIVERED', 'FAILED', 'CANCELLED')
    ),
    CONSTRAINT deliveries_assignment_fkey
        FOREIGN KEY (rout_assignmentid) REFERENCES route_assignments (roas_id) ON DELETE RESTRICT,
    CONSTRAINT deliveries_route_stop_fkey
        FOREIGN KEY (rout_stopid) REFERENCES route_stops (rost_id) ON DELETE RESTRICT
);

CREATE INDEX deliveries_parcel_idx ON deliveries (parc_id);
CREATE INDEX deliveries_assignment_idx ON deliveries (rout_assignmentid);
CREATE INDEX deliveries_route_stop_idx ON deliveries (rout_stopid);
CREATE INDEX deliveries_status_idx ON deliveries (deli_status);

CREATE TABLE incidents (
    inci_id BIGSERIAL PRIMARY KEY,
    rout_assignmentid BIGINT NOT NULL,
    deli_id BIGINT,
    inci_type VARCHAR(40) NOT NULL,
    inci_status VARCHAR(20) NOT NULL,
    inci_description VARCHAR(500) NOT NULL,
    inci_createdat TIMESTAMP NOT NULL,
    inci_resolvedat TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT incidents_type_check CHECK (
        inci_type IN (
            'VEHICLE_BREAKDOWN',
            'ACCIDENT',
            'TRAFFIC_DELAY',
            'PACKAGE_DAMAGED',
            'ADDRESS_NOT_FOUND',
            'DELIVERY_ATTEMPT_FAILED',
            'OTHER'
        )
    ),
    CONSTRAINT incidents_status_check CHECK (
        inci_status IN ('OPEN', 'IN_PROGRESS', 'RESOLVED', 'CANCELLED')
    ),
    CONSTRAINT incidents_resolved_date_check CHECK (
        inci_resolvedat IS NULL OR inci_resolvedat >= inci_createdat
    ),
    CONSTRAINT incidents_assignment_fkey
        FOREIGN KEY (rout_assignmentid) REFERENCES route_assignments (roas_id) ON DELETE RESTRICT,
    CONSTRAINT incidents_delivery_fkey
        FOREIGN KEY (deli_id) REFERENCES deliveries (deli_id) ON DELETE SET NULL
);

CREATE INDEX incidents_assignment_idx ON incidents (rout_assignmentid);
CREATE INDEX incidents_delivery_idx ON incidents (deli_id);
CREATE INDEX incidents_status_idx ON incidents (inci_status);
