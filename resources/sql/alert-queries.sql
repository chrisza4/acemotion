-- :name create-alert! :<! :1
-- :doc creates a new alert
INSERT INTO alerts
  (id, created, updated, owner_id, group_id, status)
VALUES
  (:id, current_timestamp, current_timestamp, :owner_id, :group_id, :status)
RETURNING *

-- :name get-related-alerts :? :*
-- :doc get related group by user id
SELECT alerts.*
  FROM alerts
  INNER JOIN groups on groups.id = alerts.group_id
  INNER JOIN group_members on groups.id = group_members.group_id
  WHERE group_members.user_id = :user-id OR alerts.owner_id = :user-id
  GROUP BY alerts.id

-- :name test-delete-all-alerts :! :n
DELETE FROM alerts
