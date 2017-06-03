/*Drop tables in correct suitable order as to avoid invalidating foreign key constraints*/

DROP TABLE IF EXISTS posted_comments;
DROP TABLE IF EXISTS posted_multimedia;
DROP TABLE IF EXISTS uploaded_articles;
DROP TABLE IF EXISTS registered_users;

CREATE TABLE registered_users (
  user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username VARCHAR(40) NOT NULL UNIQUE,
  hash BLOB NOT NULL,
  salt BLOB NOT NULL,
  iterations INT NOT NULL,
  email VARCHAR(200),
  phone VARCHAR(12),
  occupation VARCHAR(50),
  city VARCHAR(50),
  profile_description TEXT,
  profile_picture VARCHAR(200)
);

CREATE TABLE uploaded_articles (
  article_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  author_id INT NOT NULL,
  date DATE NOT NULL,
  article_title TEXT NOT NULL,
  article_body TEXT NOT NULL,
  FOREIGN KEY (author_id) REFERENCES registered_users(user_id)
);

CREATE TABLE posted_comments (
  comment_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  article_id INT NOT NULL,
  author_id INT NOT NULL,
  parent_comment_id INT,
  timestamp TIMESTAMP NOT NULL,
  comment_body TEXT NOT NULL,
  FOREIGN KEY (article_id) REFERENCES uploaded_articles(article_id),
  FOREIGN KEY (author_id) REFERENCES registered_users(user_id),
  FOREIGN KEY (parent_comment_id) REFERENCES posted_comments(comment_id)
);

CREATE TABLE posted_multimedia (
  multimedia_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  article_id INT NOT NULL,
  file_type VARCHAR(5),
  file_path VARCHAR(200) NOT NULL UNIQUE,
  multimedia_title TEXT
);

INSERT INTO uploaded_articles (author_id, date, article_title, article_body) VALUES (1, DATE '2017-05-31', "You're a lizard Harry!", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec pharetra erat id pellentesque rhoncus. Ut fermentum blandit risus, at blandit velit. Etiam finibus imperdiet erat, pulvinar imperdiet felis malesuada ut. Ut vel luctus mauris. Aliquam tempor, dui a laoreet pulvinar, odio ex lacinia tellus, eget lobortis velit dui vel nisl. Vestibulum non molestie nibh, in ultricies mi. Pellentesque sed sagittis felis.");


