CREATE TABLE IF NOT EXISTS orders (
                        id SERIAL PRIMARY KEY,
                        user_id INTEGER NOT NULL,
                        meals_id INTEGER[] NOT NULL,
                        price INTEGER DEFAULT 0 NOT NULL,
                        done BOOLEAN DEFAULT FALSE NOT NULL,
                        CONSTRAINT FK_PersonOrder
                            FOREIGN KEY (user_id)
                                REFERENCES app_users(id)
);