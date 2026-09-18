CREATE TABLE license_categories (
    lica_id BIGSERIAL PRIMARY KEY,
    lica_code VARCHAR(20) NOT NULL,
    lica_name VARCHAR(100) NOT NULL,
    lica_description VARCHAR(255),
    CONSTRAINT license_categories_code_ukey UNIQUE (lica_code)
);

CREATE UNIQUE INDEX license_categories_code_lower_ukey
    ON license_categories (LOWER(lica_code));

CREATE TABLE drivers (
    driv_id BIGSERIAL PRIMARY KEY,
    driv_user_id BIGINT NOT NULL,
    driv_license_number VARCHAR(30) NOT NULL,
    driv_status VARCHAR(20) NOT NULL,
    CONSTRAINT drivers_user_id_ukey UNIQUE (driv_user_id),
    CONSTRAINT drivers_license_number_ukey UNIQUE (driv_license_number),
    CONSTRAINT drivers_status_check CHECK (
        driv_status IN ('AVAILABLE', 'ASSIGNED', 'DRIVING', 'ON_LEAVE', 'SUSPENDED', 'INACTIVE')
    )
);

CREATE UNIQUE INDEX drivers_license_number_lower_ukey
    ON drivers (LOWER(driv_license_number));

CREATE TABLE driver_license_categories (
    drli_driver_id BIGINT NOT NULL,
    drli_license_category_id BIGINT NOT NULL,
    PRIMARY KEY (drli_driver_id, drli_license_category_id),
    CONSTRAINT driver_license_categories_driver_fkey
        FOREIGN KEY (drli_driver_id)
        REFERENCES drivers (driv_id)
        ON DELETE CASCADE,
    CONSTRAINT driver_license_categories_category_fkey
        FOREIGN KEY (drli_license_category_id)
        REFERENCES license_categories (lica_id)
        ON DELETE RESTRICT
);

CREATE INDEX driver_license_categories_category_idx
    ON driver_license_categories (drli_license_category_id);
