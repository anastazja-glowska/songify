create extension if not exists "uuid-ossp";


ALTER TABLE users
    ADD created_on TIMESTAMP(6) WITH TIME ZONE default now();

ALTER TABLE users
    ADD uuid UUID default uuid_generate_v4() not null unique ;

ALTER TABLE users
    ADD version BIGINT not null default 0;

