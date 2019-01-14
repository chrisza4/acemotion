-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass, salt, is_active)
VALUES (:id, :first_name, :last_name, :email, :pass, :salt, :is_active)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name get-user-by-email :? :1
-- :doc retrives a user record given email
SELECT * FROM users
WHERE email = :email

-- :name get-all-users :? :*
SELECT * FROM users

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id

-- :name delete-all-users! :! :n
-- :doc deletes all users, use for testing
DELETE FROM users
