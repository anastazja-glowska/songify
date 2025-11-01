-- CREATE SEQUENCE IF NOT EXISTS song_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE song
(
    id            BIGSERIAL       PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    release_date  TIMESTAMP WITHOUT TIME ZONE,
    duration      BIGINT,
    song_language VARCHAR(255)

);