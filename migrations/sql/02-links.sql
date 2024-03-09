create table public.links
(
    id          serial
        constraint links_pk
            primary key,
    last_check  timestamp with time zone not null,
    last_update timestamp with time zone not null,
    site        varchar                  not null,
    url         text                     not null
);

create index links_last_check_index
    on links (last_check);

create unique index links_url_uindex
    on public.links (url);
