create table if not exists accounts
(
    id       uuid default gen_random_uuid() primary key,
    username varchar(100) not null,
    password varchar(255) not null,
    role     varchar(100) not null
);

create unique index if not exists accounts_username_idx on accounts (username);