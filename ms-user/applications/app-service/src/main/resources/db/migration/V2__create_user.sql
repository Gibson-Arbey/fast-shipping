

CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    user_name varchar(255) NOT NULL,
    user_lastname varchar(255) NOT NULL,
    user_email varchar(255) NOT NULL,
    user_password varchar(255) NOT NULL,
    role_id BIGINT NOT NULL,
    user_status varchar(255) NOT NULL,
    user_createdat TIMESTAMPTZ NOT NULL,
    CONSTRAINT users_ukey_email UNIQUE (user_email),
    CONSTRAINT users_role_fkey FOREIGN KEY (role_id) REFERENCES roles(role_id)
);