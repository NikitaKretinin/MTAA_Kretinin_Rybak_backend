CREATE TABLE meals (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR UNIQUE NOT NULL,
                       description VARCHAR NOT NULL,
                       price INTEGER NOT NULL
--                        photo BYTEA
);