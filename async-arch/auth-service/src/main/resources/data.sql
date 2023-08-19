insert into auth_service.accounts(username, password, role) values ('admin-popug', '$2a$10$SCgJAupIckchu7sQkYOqw.5d21iZGS.i0qVwOz2uJv4NtBiiiOPB6', 'ADMIN') on conflict do nothing;

insert into auth_service.accounts(username, password, role) values ('boss-popug', '$2a$10$SCgJAupIckchu7sQkYOqw.5d21iZGS.i0qVwOz2uJv4NtBiiiOPB6', 'MANAGER') on conflict do nothing;
