CREATE TABLE app_users (
                       id SERIAL PRIMARY KEY,
                       login VARCHAR UNIQUE NOT NULL,
                       password VARCHAR NOT NULL,
                       user_role VARCHAR NOT NULL
);