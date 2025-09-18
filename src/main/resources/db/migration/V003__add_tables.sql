CREATE SEQUENCE IF NOT EXISTS cart_items_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS orders_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS order_details_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS cart_items
(
    id bigint NOT NULL,
    quantity smallint NOT NULL,
    product_id bigint,
    user_id bigint,

    created TIMESTAMP NOT NULL DEFAULT NOW(),
    modified TIMESTAMP NOT NULL DEFAULT NOW(),
    changed_by VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT cart_items_pkey PRIMARY KEY (id),
    CONSTRAINT fk_cart_items_users FOREIGN KEY (user_id)
    REFERENCES users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_cart_items_product FOREIGN KEY (product_id)
    REFERENCES product (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS orders
(
    id bigint NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT NOW(),
    total NUMERIC(12,2) NOT NULL,
    order_status VARCHAR(50) NOT NULL,
    user_id bigint,

    created TIMESTAMP NOT NULL DEFAULT NOW(),
    modified TIMESTAMP NOT NULL DEFAULT NOW(),
    changed_by VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT fk_orders_users FOREIGN KEY (user_id)
    REFERENCES users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS order_details
(
    id bigint NOT NULL,
    quantity smallint NOT NULL,
    unit_price NUMERIC(12,2) NOT NULL,
    subtotal NUMERIC(12,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    order_id bigint,
    product_id bigint,

    created TIMESTAMP NOT NULL DEFAULT NOW(),
    modified TIMESTAMP NOT NULL DEFAULT NOW(),
    changed_by VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT order_details_pkey PRIMARY KEY (id),
    CONSTRAINT fk_order_details_product FOREIGN KEY (product_id)
    REFERENCES product (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_order_details_order FOREIGN KEY (order_id)
    REFERENCES orders (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);