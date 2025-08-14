insert into
    users(id, birth_date, gender)
values
    ('a2f9b586-4d3e-439d-9626-043d3f7165c9'::uuid, '1990-01-01'::date, 'MALE'::gender);

insert into
    insights(id, title, date, author_id, content)
values
    (
     'c0a3812d-2f7e-4043-bc78-4dfa53d08dff'::uuid,
     'Insight Title',
     '2025-08-14 10:00:00'::timestamp,
     'a2f9b586-4d3e-439d-9626-043d3f7165c9'::uuid,
     'Insight Content'
    );

insert into
    analyses(id, user_id, insight_id, analysis)
values
    (
     'd5a4f8e2-7c31-4b9d-ae56-1f9c2b803e5d'::uuid,
     'a2f9b586-4d3e-439d-9626-043d3f7165c9'::uuid,
     'c0a3812d-2f7e-4043-bc78-4dfa53d08dff'::uuid,
     'Analysis Content'
    );