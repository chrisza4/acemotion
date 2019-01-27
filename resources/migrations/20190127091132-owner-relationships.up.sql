ALTER TABLE alerts
  ADD CONSTRAINT fk__alerts__users__owner_id
  FOREIGN KEY (owner_id) REFERENCES users (id);
--;;
ALTER TABLE group_members
  ADD CONSTRAINT fk__group_members__users__user_id
  FOREIGN KEY (user_id) REFERENCES users (id);
--;;
ALTER TABLE group_members
  ADD CONSTRAINT fk__group_members__groups__group_id
  FOREIGN KEY (group_id) REFERENCES groups (id);
--;;
ALTER TABLE groups
  ADD CONSTRAINT fk__groups__users__owner_id
  FOREIGN KEY (owner_id) REFERENCES users (id);
