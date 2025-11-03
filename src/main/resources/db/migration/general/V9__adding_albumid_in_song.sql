

ALTER TABLE song
    ADD album_id Bigint REFERENCES album (id);
