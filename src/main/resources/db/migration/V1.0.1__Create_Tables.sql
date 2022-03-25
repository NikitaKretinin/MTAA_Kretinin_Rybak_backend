DROP TABLE IF EXISTS
    users,
    meals,
    orders,
    orders_meals
    CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    login VARCHAR UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    user_role VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS meals (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    description VARCHAR NOT NULL,
    price INTEGER NOT NULL,
    photo bytea
);

CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    price INTEGER DEFAULT 0 NOT NULL,
    pay_by_cash BOOLEAN NOT NULL,
    done BOOLEAN DEFAULT FALSE NOT NULL,
    CONSTRAINT FK_PersonOrder
        FOREIGN KEY (user_id)
            REFERENCES users(id)
);

CREATE TABLE orders_meals (
                              id SERIAL PRIMARY KEY,
                              order_id INTEGER NOT NULL,
                              meal_id INTEGER NOT NULL,
                              CONSTRAINT FK_OrderRelation
                                  FOREIGN KEY (order_id)
                                      REFERENCES orders(id),
                              CONSTRAINT FK_MealRelation
                                  FOREIGN KEY (meal_id)
                                      REFERENCES meals(id)
);