insert into users (username,password,enabled) values ('user1','$2a$10$HU78NWoxd3GVwMai2hyZae45bW8mebvidUS2GCXck2R3EoxSzBDMi',true);
insert into authorities(username,authority) values ('user1','ROLE_USER');

-- Create the Groups
insert into groups(group_name) values ('Users');
insert into groups(group_name) values ('Administrators');

-- Map the Groups to Roles
insert into group_authorities(group_id, authority) select id,'ROLE_USER' from groups where group_name='Users';
insert into group_authorities(group_id, authority) select id,'ROLE_USER' from groups where group_name='Administrators';
insert into group_authorities(group_id, authority) select id,'ROLE_ADMIN' from groups where group_name='Administrators';

-- Map the users to Groups
insert into group_members(group_id, username) select id,'user1' from groups where group_name='Users';
insert into group_members(group_id, username) select id,'admin1' from groups where group_name='Administrators';
