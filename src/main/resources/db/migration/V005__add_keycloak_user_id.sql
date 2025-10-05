ALTER TABLE users
    ADD COLUMN if not exists keycloak_id VARCHAR(36),
    ADD COLUMN if not exists role VARCHAR(10);

