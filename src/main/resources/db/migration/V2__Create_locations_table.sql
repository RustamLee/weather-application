CREATE TABLE locations (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                           latitude DECIMAL(10, 7) NOT NULL,
                           longitude DECIMAL(10, 7) NOT NULL
);