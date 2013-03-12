-- You can use this file to load seed data into the database using SQL statements
insert into User (id, name, email, password) values (0, 'John Smith', 'foo@example.com', 'hello'); 

insert into Question (id, name) values (0, 'Where is it?');
insert into Question (id, name) values (1, 'How is it?');

insert into Survey (id, name, description) values (1, 'Location survey', 'Just a testing survey.');
