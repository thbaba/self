CREATE TYPE gender as ENUM ('MALE', 'FEMALE', 'OTHER');

CREATE TABLE users (
    user_id uuid primary key,
    birth_date date not null,
    gender gender not null
);