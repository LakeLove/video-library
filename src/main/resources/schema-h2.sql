DROP TABLE videos;

CREATE TABLE videos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(55) NOT NULL DEFAULT '',
    path VARCHAR(255) NOT NULL DEFAULT '',
    author VARCHAR(55) NOT NULL DEFAULT '',
    date_uploaded DATE NOT NULL,
    description TEXT,
    PRIMARY KEY (id));

DROP TABLE comments;

CREATE TABLE comments (
    comment_id BIGINT NOT NULL AUTO_INCREMENT,
    comment_text TEXT,
    comment_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    video_id BIGINT NOT NULL,
    PRIMARY KEY (comment_id));

DROP TABLE users;

CREATE TABLE users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(32),
    password VARCHAR(32),
    PRIMARY KEY (user_id));



DROP SEQUENCE hibernate_sequence;

CREATE SEQUENCE hibernate_sequence;
