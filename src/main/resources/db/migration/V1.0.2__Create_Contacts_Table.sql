DROP TABLE IF EXISTS contacts CASCADE;

CREATE TABLE IF NOT EXISTS contacts (
                                     id SERIAL PRIMARY KEY,
                                     address VARCHAR NOT NULL,
                                     phone VARCHAR
);

ALTER TABLE users ADD COLUMN contact_id INTEGER,
    ADD CONSTRAINT FK_PersonContact
        FOREIGN KEY (contact_id)
            REFERENCES contacts(id);

