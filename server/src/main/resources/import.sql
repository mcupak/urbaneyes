-- You can use this file to load seed data into the database using SQL statements
insert into User (id, name, email, password) values (0, 'John Smith', 'foo@example.com', 'hello'); 

insert into Survey (id, name, priv, description) values (0, 'Sample Survey', false, 'This is a sample survey.'); 
insert into User_Survey (User_id, Owned_id, Contributed_id) values (0, 0, 0);

insert into Point (id, name, latitude, longitude, altitude) values (0, 'Sample point', 0, 0, 0);