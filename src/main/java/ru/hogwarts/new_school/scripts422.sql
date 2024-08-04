DROP TABLE IF EXISTS car;

CREATE TABLE car(
    id SERIAL PRIMARY KEY,
    brand VARCHAR(30) NOT NULL,
    model VARCHAR(30) NOT NULL,
    cost INT NOT NULL
);

DROP TABLE IF EXISTS person;

CREATE TABLE person(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    age INT NOT NULL CHECK (age > 18),
    car_licence BOOLEAN NOT NULL,
    car_id INT REFERENCES car (id)
);


