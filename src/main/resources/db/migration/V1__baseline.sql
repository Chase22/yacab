CREATE TABLE telegram_group (
    id bigint primary key
);
CREATE TABLE active_verification (
    id uuid primary key,
    user_id int,
    chat_id bigint,
    valid_until timestamp,
    welcome_message_id int
);