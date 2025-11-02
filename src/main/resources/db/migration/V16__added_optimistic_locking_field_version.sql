ALTER TABLE album
    ADD version BIGINT default 0;

ALTER TABLE artist
    ADD version BIGINT default 0;

ALTER TABLE genre
    ADD version BIGINT default 0;

ALTER TABLE song
    ADD version BIGINT default 0;

ALTER TABLE genre
    ALTER COLUMN version set NOT NULL;

ALTER TABLE album
    ALTER COLUMN version set NOT NULL;

ALTER TABLE artist
    ALTER COLUMN version set NOT NULL;

ALTER TABLE genre
    ALTER COLUMN version set NOT NULL;

ALTER TABLE song
    ALTER COLUMN version set NOT NULL;