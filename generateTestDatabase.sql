/*Drop tables in correct suitable order as to avoid invalidating foreign key constraints*/

DROP TABLE IF EXISTS posted_comments;
DROP TABLE IF EXISTS posted_multimedia;
DROP TABLE IF EXISTS uploaded_articles;
DROP TABLE IF EXISTS registered_users;

CREATE TABLE registered_users (
  user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username VARCHAR(40) NOT NULL UNIQUE,
  nickname VARCHAR(40),
  hash BLOB NOT NULL,
  salt BLOB NOT NULL,
  iterations INT NOT NULL
);



