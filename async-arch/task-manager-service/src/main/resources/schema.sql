create schema if not exists task_manager_service;

create table if not exists task_manager_service.accounts
(
    id   uuid default gen_random_uuid() primary key,
    role varchar(100) not null
);

create table if not exists task_manager_service.tasks
(
    id          uuid default gen_random_uuid() primary key,
    description varchar(255) not null,
    account_id  uuid         not null,
    status      varchar(100) not null,
    constraint fk_account
        foreign key (account_id)
            references task_manager_service.accounts (id)
);