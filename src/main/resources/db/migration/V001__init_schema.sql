CREATE SEQUENCE IF NOT EXISTS product_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS technical_details_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS users_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS inventories_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS product
(
  id bigint NOT NULL,
	name VARCHAR(255) NOT NULL,
	code VARCHAR(64) NOT NULL UNIQUE,
	description TEXT,
	price NUMERIC(10,2) NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS technical_details
(
    id bigint NOT NULL,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255),
    product_id bigint NOT NULL,
    CONSTRAINT technical_details_pkey PRIMARY KEY (id),
    CONSTRAINT fk_technical_details_product FOREIGN KEY (product_id)
        REFERENCES product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS users
(
    id bigint NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS inventories
(
    id bigint NOT NULL,
    code VARCHAR(255) NOT NULL UNIQUE,
    quantity bigint,
    product_id bigint NOT NULL,
    CONSTRAINT inventories_pkey PRIMARY KEY (id)
);


