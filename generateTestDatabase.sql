/*Drop tables in correct suitable order as to avoid invalidating foreign key constraints*/

DROP TABLE IF EXISTS posted_comments;
DROP TABLE IF EXISTS posted_multimedia;
DROP TABLE IF EXISTS uploaded_articles;
DROP TABLE IF EXISTS registered_users;

CREATE TABLE registered_users (
  user_id             INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username            VARCHAR(40)     NOT NULL UNIQUE,
  hash                BLOB            NOT NULL,
  salt                BLOB            NOT NULL,
  iterations          INT             NOT NULL,
  email               VARCHAR(200),
  phone               VARCHAR(12),
  occupation          VARCHAR(50),
  city                VARCHAR(50),
  profile_description TEXT,
  profile_picture     VARCHAR(200),
  firstname           VARCHAR(60),
  lastname            VARCHAR(60)
);

CREATE TABLE uploaded_articles (
  article_id    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  author_id     INT             NOT NULL,
  timestamp     TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  article_title VARCHAR(500)    NOT NULL,
  article_body  TEXT            NOT NULL,
  FOREIGN KEY (author_id) REFERENCES registered_users (user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE posted_comments (
  comment_id        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  article_id        INT             NOT NULL,
  author_id         INT             NOT NULL,
  parent_comment_id INT,
  timestamp         TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  comment_body      TEXT            NOT NULL,
  is_parent         BOOLEAN,
  FOREIGN KEY (article_id) REFERENCES uploaded_articles (article_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (author_id) REFERENCES registered_users (user_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (parent_comment_id) REFERENCES posted_comments (comment_id)
  ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE posted_multimedia (
  multimedia_id    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  article_id       INT             NOT NULL,
  file_type        VARCHAR(5),
  file_path        VARCHAR(500)   NOT NULL UNIQUE,
  multimedia_title TEXT,
  FOREIGN KEY (article_id) REFERENCES uploaded_articles (article_id)
  ON DELETE CASCADE ON UPDATE CASCADE
);