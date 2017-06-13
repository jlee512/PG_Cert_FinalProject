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
  FOREIGN KEY (author_id) REFERENCES registered_users (user_id),
  CONSTRAINT uniqueArticle UNIQUE
  (
    timestamp, article_title
  )
);

CREATE TABLE posted_comments (
  comment_id        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  article_id        INT             NOT NULL,
  author_id         INT             NOT NULL,
  parent_comment_id INT,
  timestamp         TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  comment_body      TEXT            NOT NULL,
  is_parent         BOOLEAN,
  FOREIGN KEY (article_id) REFERENCES uploaded_articles (article_id),
  FOREIGN KEY (author_id) REFERENCES registered_users (user_id),
  FOREIGN KEY (parent_comment_id) REFERENCES posted_comments (comment_id)
  ON DELETE CASCADE
);

CREATE TABLE posted_multimedia (
  multimedia_id    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  article_id       INT             NOT NULL,
  file_type        VARCHAR(5),
  file_path        VARCHAR(200)    NOT NULL UNIQUE,
  multimedia_title TEXT
);

/*---------------------------------------------------TEST QUERIES---------------------------------------------------*/
#
# SELECT article_id, username, firstname, lastname, timestamp, article_title, Substring(article_body, 1, 10) FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id
# ORDER BY timestamp
# LIMIT 3 OFFSET 1;
#
# /*Test query of articles*/
# SELECT article_id, username, firstname, lastname, timestamp, article_title, SubString(article_body, 1, 10) AS article_preview FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id
# ORDER BY timestamp
# LIMIT 3 OFFSET 1;
#
# /*Test query of articles joined to users and comments*/
# SELECT a.article_id, u.username, u.firstname, u.lastname, a.timestamp, a.article_title, a.article_body, c.comment_id, c.comment_body AS individual_article
# FROM uploaded_articles AS a
# LEFT JOIN registered_users AS u ON a.author_id = u.user_id
# LEFT JOIN posted_comments AS c ON a.article_id = c.article_id
# WHERE a.article_id = 4 AND parent_comment_id IS NULL
# ORDER BY timestamp;
#
# SELECT comment_id parent_comment_id, timestamp, comment_body, is_parent, username, firstname, lastname FROM posted_comments LEFT JOIN registered_users ON posted_comments.author_id = registered_users.user_id WHERE article_id = 4 AND parent_comment_id IS NULL ORDER BY TIMESTAMP LIMIT 5 OFFSET 0;
#
# SELECT parent_comment_id FROM posted_comments WHERE comment_id = 10;
#
#
# SELECT article_id, username, firstname, lastname, timestamp, article_title, SubString(article_body, 1, 100) AS article_preview FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id WHERE user_id = 1 ORDER BY timestamp DESC LIMIT 3 OFFSET 0;

SELECT COUNT(username) FROM registered_users;

UPDATE uploaded_articles SET timestamp = CURRENT_TIMESTAMP, article_title = 'TEST', article_body = 'TESTtestTEST'  WHERE article_id = 89 AND author_id = 3;
