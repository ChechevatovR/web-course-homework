create table public.chats
(
    id               serial
        constraint chats_pk
            primary key,
    telegram_chat_id integer not null
);
