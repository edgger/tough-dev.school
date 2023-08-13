insert into accounts(username, password, role) values ('user1', '$2a$10$SCgJAupIckchu7sQkYOqw.5d21iZGS.i0qVwOz2uJv4NtBiiiOPB6', 'ADMIN') on conflict do nothing;

insert into accounts(username, password, role) values ('user2', '$2a$10$SCgJAupIckchu7sQkYOqw.5d21iZGS.i0qVwOz2uJv4NtBiiiOPB6', 'MANAGER') on conflict do nothing;

insert into accounts(username, password, role) values ('user3', '$2a$10$SCgJAupIckchu7sQkYOqw.5d21iZGS.i0qVwOz2uJv4NtBiiiOPB6', 'WORKER') on conflict do nothing;

insert into accounts(username, password, role) values ('user4', '$2a$10$SCgJAupIckchu7sQkYOqw.5d21iZGS.i0qVwOz2uJv4NtBiiiOPB6', 'WORKER') on conflict do nothing;
