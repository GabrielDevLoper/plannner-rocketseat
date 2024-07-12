CREATE TABLE participants
(
    id           UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    is_confirmed BOOLEAN      NOT NULL,
    name         VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    trip_id      UUID,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);