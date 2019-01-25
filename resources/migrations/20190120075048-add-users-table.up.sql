CREATE TABLE alerts
(id uuid PRIMARY KEY,
 created TIMESTAMP WITHOUT TIME ZONE DEFAULT current_timestamp,
 updated TIMESTAMP WITHOUT TIME ZONE DEFAULT current_timestamp,
 owner_id uuid,
 group_id uuid,
 status integer)
