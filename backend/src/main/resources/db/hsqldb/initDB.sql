DROP TABLE sct IF EXISTS;

CREATE TABLE sct (
  id         INTEGER IDENTITY PRIMARY KEY,
  no         VARCHAR(30),
  name       VARCHAR(30),
  depart     VARCHAR(30),
  course     VARCHAR(30),
  grade      INTEGER
);
