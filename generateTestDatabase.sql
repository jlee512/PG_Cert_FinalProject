/*Drop tables in correct suitable order as to avoid invalidating foreign key constraints*/

DROP TABLE IF EXISTS posted_comments;
DROP TABLE IF EXISTS posted_multimedia;
DROP TABLE IF EXISTS uploaded_articles;
DROP TABLE IF EXISTS registered_users;



