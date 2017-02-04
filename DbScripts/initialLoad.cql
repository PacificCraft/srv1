insert into Role (roleId, description, permissions) values (829f830a-c2a5-4a72-b774-86cd5c0fdc22, 'All permissions', ['Permission1','Permission2','Permission3']);

insert into User (userId, passwordHash, roleId, blocked, displayName) values ('c6326cc9-7713-4cef-8642-fd97ff1e5806', null, 829f830a-c2a5-4a72-b774-86cd5c0fdc22, false, 'Stefan');
insert into User (userId, passwordHash, roleId, blocked, displayName) values ('924b4a33-1efa-469d-a681-b30662a159a5', null, 829f830a-c2a5-4a72-b774-86cd5c0fdc22, false, 'Goran');
