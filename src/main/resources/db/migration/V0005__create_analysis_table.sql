create table analyses(
    id uuid primary key,
    user_id uuid not null,
    insight_id uuid not null,
    analysis varchar not null,
    constraint fk_users foreign key(user_id) references users(id),
    constraint fk_insights foreign key(insight_id) references insights(id)
);