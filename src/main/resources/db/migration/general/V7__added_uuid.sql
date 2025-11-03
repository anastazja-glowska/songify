create extension if not exists "uuid-ossp";

ALTER TABLE album
    ADD created_on TIMESTAMP(6) WITH TIME ZONE default now();

ALTER TABLE album
    ADD uuid UUID default uuid_generate_v4() not null unique ;

ALTER TABLE artist
    ADD created_on TIMESTAMP(6) WITH TIME ZONE default now();

ALTER TABLE artist
    ADD uuid UUID default uuid_generate_v4() not null unique;

ALTER TABLE genre
    ADD created_on TIMESTAMP(6) WITH TIME ZONE default now();

ALTER TABLE genre
    ADD uuid UUID default uuid_generate_v4() not null unique ;
--
-- ALTER TABLE genre
--     ALTER COLUMN name DROP NOT NULL;

