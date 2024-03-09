create table public.chats
(
    id               serial
        constraint chats_pk
            primary key,
    telegram_chat_id bigint not null
);

create unique index chats_telegram_chat_id_uindex
    on public.chats (telegram_chat_id);
