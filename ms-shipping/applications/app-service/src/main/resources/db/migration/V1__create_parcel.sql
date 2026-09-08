CREATE TABLE parcels (
    parc_id BIGSERIAL PRIMARY KEY,
    parc_trackingnumber UUID,
    user_id BIGINT NOT NULL,
    parc_weight NUMERIC(10,2) NOT NULL CHECK (parc_weight > 0),
    parc_height NUMERIC(10,2) NOT NULL CHECK (parc_height > 0),
    parc_width NUMERIC(10,2) NOT NULL CHECK (parc_width > 0),
    parc_length NUMERIC(10,2) NOT NULL CHECK (parc_length > 0),
    parc_clasificationtamanho VARCHAR(20) NOT NULL
        CHECK (
            parc_clasificationtamanho IN (
                'SMALL',
                'MEDIUM',
                'LARGE',
                'EXTRA_LARGE'
            )
        ),
    parc_type VARCHAR(20) NOT NULL CHECK (
        parc_type IN (
            'STANDARD',
            'FRAGILE',
            'PRIORITY'
        )
    ),
    parc_description VARCHAR(255)
);