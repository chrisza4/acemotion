-- :name create-group! :! :n
-- :doc creates a new user record
INSERT INTO groups
  (id, created, updated, owner_id)
VALUES
  (:id, current_timestamp, current_timestamp, :owner_id)

-- :name add-group-member! :! :n
-- :doc insert member into group
INSERT INTO group_members
  (id, created, group_id, user_id)
VALUES
  (:id, current_timestamp, :group_id, :user_id)

-- :name get-related-groups :? :*
-- :doc get related group
SELECT groups.id, groups.owner_id, groups.created, groups.updated
  FROM groups
  LEFT JOIN group_members ON (groups.id = group_members.group_id)
  WHERE groups.owner_id = :user_id OR group_members.user_id = :user_id
  GROUP BY groups.id

-- :name get-group-member-ids :? :*
SELECT user_id
  FROM group_members
  WHERE group_id = :group_id

-- :name test-delete-all-groups! :! :n
-- :doc Delete all groups
DELETE FROM group_members;
DELETE FROM groups;
