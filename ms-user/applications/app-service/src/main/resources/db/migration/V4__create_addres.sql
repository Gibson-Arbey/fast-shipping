CREATE TABLE addresses (
    addr_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    addr_street VARCHAR(150) NOT NULL,
    addr_number VARCHAR(20) NOT NULL,
    addr_neighborhood VARCHAR(100),
    addr_city VARCHAR(100) NOT NULL,
    addr_state VARCHAR(100) NOT NULL,
    addr_country VARCHAR(100) NOT NULL,
    addr_postalcode VARCHAR(20),
    addr_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_address_user
        FOREIGN KEY (user_id)
        REFERENCES users (user_id)
);