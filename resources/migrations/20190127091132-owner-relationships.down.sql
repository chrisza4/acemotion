ALTER TABLE alerts DROP CONSTRAINT fk__alerts__users__owner_id;
--;;
ALTER TABLE group_members DROP CONSTRAINT fk__group_members__users__user_id;
--;;
ALTER TABLE group_members DROP CONSTRAINT fk__group_members__groups__group_id;
--;;
ALTER TABLE groups DROP CONSTRAINT fk__groups__users__owner_id;
