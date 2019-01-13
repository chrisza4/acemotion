CREATE TABLE users
(id uuid PRIMARY KEY,
 first_name VARCHAR(30),
 last_name VARCHAR(30),
 email VARCHAR(30),
 last_login TIMESTAMP,
 is_active BOOLEAN,
 pass VARCHAR(300),
 salt VARCHAR(300));
