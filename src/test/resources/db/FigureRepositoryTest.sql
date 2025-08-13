create type
    figuretype
as enum
    ('ARCHETYPE', 'SYMBOL');

CREATE TABLE
    figures(
        id uuid primary key default gen_random_uuid(),
        name varchar(32) not null,
        brief varchar(128) not null,
        description varchar(512) not null,
        figure figuretype not null
    );

INSERT INTO
    figures (name, brief, description, figure)
VALUES
    ('Archetype1', 'ArchetypeBrief1', 'ArchetypeDescription1', 'ARCHETYPE'),
    ('Archetype2', 'ArchetypeBrief2', 'ArchetypeDescription2', 'ARCHETYPE'),
    ('Archetype3', 'ArchetypeBrief3', 'ArchetypeDescription3', 'ARCHETYPE'),
    ('Archetype4', 'ArchetypeBrief4', 'ArchetypeDescription4', 'ARCHETYPE');

INSERT INTO
    figures (name, brief, description, figure)
VALUES
    ('Symbol1', 'SymbolBrief1', 'SymbolDescription1', 'SYMBOL'),
    ('Symbol2', 'SymbolBrief2', 'SymbolDescription1', 'SYMBOL'),
    ('Symbol3', 'SymbolBrief3', 'SymbolDescription1', 'SYMBOL'),
    ('Symbol4', 'SymbolBrief4', 'SymbolDescription1', 'SYMBOL');