create table public.tracking
(
    chat_id integer not null
        constraint tracking_chats_id_fk
            references public.chats
            on update restrict on delete restrict,
    link_id integer not null
        constraint tracking_links_id_fk
            references public.links,
    constraint tracking_pk
        primary key (chat_id, link_id)
);
