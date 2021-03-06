#
#  INSERT INTO users VALUES
#   ('john', '{noop}test123', 1),
#   ('mary', '{noop}test123', 1),
#   ('susan', '{noop}test123', 1);

USE USERSECURITY;

INSERT INTO users VALUES
  ('john', '{bcrypt}$2a$04$yq0EW76JokEFPtTayKeq8OD2E1B2gGfzNBds93b9TL5uw9RKehRSy', 1),
  ('mary', '{bcrypt}$2a$04$cVN1PSDOdWMukdKN3ZV2meX8OGZhRhsJYGQv1B.GpPJxrKuCeKXNy', 1),
  ('susan', '{bcrypt}$2a$04$nHtRTqLwo26n9s2v5nsQFelbhyJQnQq38F103swzI5P6VgYgNfFrK', 1);

INSERT INTO authorities VALUES
  ('john', 'ROLE_HUHA'),
  ('john', 'ROLE_EMPLOYEE'),
  ('mary', 'ROLE_MANAGER'),
  ('mary', 'ROLE_EMPLOYEE'),
  ('susan', 'ROLE_ADMIN'),
  ('susan', 'ROLE_EMPLOYEE');