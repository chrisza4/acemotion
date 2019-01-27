CREATE TABLE groups
(id uuid PRIMARY KEY,
 created TIMESTAMP WITHOUT TIME ZONE DEFAULT current_timestamp,
 updated TIMESTAMP WITHOUT TIME ZONE DEFAULT current_timestamp,
 owner_id uuid);
--;;
CREATE TABLE group_members
(id uuid PRIMARY KEY,
 created TIMESTAMP WITHOUT TIME ZONE DEFAULT current_timestamp,
 group_id uuid,
 user_id uuid);
--;;
CREATE INDEX idx__group_members__group_id ON group_members (group_id);
--;;
CREATE INDEX idx__group_members__user_id ON group_members (user_id);
--;;
CREATE INDEX idx__groups__owner_id ON groups (owner_id);
-- CREATE INDEX ON GROUP ID in group_members
-- CREATE Proper relationship
