CREATE TABLE devices
(id uuid PRIMARY KEY,
 created TIMESTAMP WITHOUT TIME ZONE DEFAULT current_timestamp,
 updated TIMESTAMP WITHOUT TIME ZONE DEFAULT current_timestamp,
 owner_id uuid,
 token varchar NOT NULL DEFAULT '',
 remark varchar,
 type integer NOT NULL,
 FOREIGN KEY (owner_id) REFERENCES users (id));
 --;;
 CREATE INDEX idx__devices__owner_id ON devices (owner_id);
