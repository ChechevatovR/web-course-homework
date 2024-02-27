create table public.links
(
    id         serial
        constraint links_pk
        primary key,
    last_check timestamp with time zone not null,
    site       varchar                  not null,
    url        text                     not null
);
