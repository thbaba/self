create table insights(
    id uuid primary key default gen_random_uuid(),
    title varchar(32) not null,
    date timestamp not null,
    author_id uuid not null,
    content varchar(256) not null,
    constraint fk_users foreign key(author_id) references users(id)
);