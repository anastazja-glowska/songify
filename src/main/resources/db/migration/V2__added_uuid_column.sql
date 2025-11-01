create extension if not exists "uuid-ossp";


ALTER TABLE song
    ADD uuid UUID default uuid_generate_v4() not null unique ;