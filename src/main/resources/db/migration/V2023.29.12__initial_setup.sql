CREATE TABLE IF NOT EXISTS limit_order_entity
(
    id               BIGINT PRIMARY KEY generated by default as identity,
    currency_chain   VARCHAR(255) NOT NULL,
    operation        VARCHAR(255) NOT NULL,
    time_in_force    VARCHAR(255) NOT NULL,
    quantity         NUMERIC      NOT NULL,
    price            NUMERIC      NOT NULL,
    order_id         BIGINT       NOT NULL,
    order_list_id    BIGINT       NOT NULL,
    type             VARCHAR(255) NOT NULL,
    stop_price       NUMERIC      NOT NULL,
    stop_limit_price NUMERIC      NOT NULL,
    order_stop_id    BIGINT       NOT NULL,
    stop_type        VARCHAR(255) NOT NULL

);


