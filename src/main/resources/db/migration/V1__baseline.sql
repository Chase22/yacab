CREATE TABLE telegram_group
(
    id     bigint primary key,
    paused bool not null
);
CREATE TABLE active_verification
(
    id                 uuid primary key,
    user_id            int not null,
    chat_id            bigint not null,
    valid_until        timestamp not null,
    welcome_message_id int not null
);