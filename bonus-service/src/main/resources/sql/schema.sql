-- DROP TABLE IF EXISTS privilege_history;
-- DROP TABLE IF EXISTS privilege;

CREATE TABLE IF NOT EXISTS privilege
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(80) NOT NULL UNIQUE,
    status   VARCHAR(80) NOT NULL DEFAULT 'BRONZE'
        CHECK (status IN ('BRONZE', 'SILVER', 'GOLD')),
    balance  INT
);

CREATE TABLE IF NOT EXISTS privilege_history
(
    id             SERIAL PRIMARY KEY,
    privilege_id   INT REFERENCES privilege (id),
    ticket_uid     uuid        NOT NULL,
    datetime       TIMESTAMP WITHOUT TIME ZONE   NOT NULL,
    balance_diff   INT         NOT NULL,
    operation_type VARCHAR(20) NOT NULL
        CHECK (operation_type IN ('FILL_IN_BALANCE', 'DEBIT_THE_ACCOUNT'))
);

INSERT INTO privilege VALUES(1, 'Test Max', 'BRONZE', 0) ON CONFLICT (id) do nothing;
