DROP TABLE IF EXISTS flight;
DROP TABLE IF EXISTS airport;

CREATE TABLE IF NOT EXISTS airport
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255),
    city    VARCHAR(255),
    country VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS flight
(
    id              SERIAL PRIMARY KEY,
    flight_number   VARCHAR(20)              NOT NULL,
    datetime        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    from_airport_id INT REFERENCES airport (id),
    to_airport_id   INT REFERENCES airport (id),
    price           INT                      NOT NULL
);

INSERT INTO airport VALUES(1, 'Шереметьево', 'Москва', 'Россия');
INSERT INTO airport VALUES(2, 'Пулково', 'Санкт-Петербург', 'Россия');

INSERT INTO flight VALUES(1, 'AFL031', '2021-10-08 20:00', 2, 1, 1500);
