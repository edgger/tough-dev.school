create schema if not exists billing_service;

create table if not exists billing_service.accounts
(
    id      uuid default gen_random_uuid() primary key,
    role    varchar(100)   not null,
    balance decimal(12, 2) not null
);

create table if not exists billing_service.tasks
(
    id            uuid default gen_random_uuid() primary key,
    description   varchar(255)   not null,
    account_id    uuid           not null,
    cost_assign   decimal(12, 2) not null,
    cost_complete decimal(12, 2) not null,
    constraint fk_account
        foreign key (account_id)
            references billing_service.accounts (id)
);