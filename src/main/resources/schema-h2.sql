DROP TABLE videos;

CREATE TABLE videos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(55) NOT NULL DEFAULT,
    path VARCHAR(255) NOT NULL DEFAULT,
    author VARCHAR(55) NOT NULL DEFAULT '',
    date_uploaded DATE NOT NULL,
    description TEXT DEFAULT '',
    PRIMARY KEY (id);
)

DROP TABLE comments;

CREATE TABLE comments (
    comment_id BIGINT NOT NULL AUTO_INCREMENT,
    comment_text TEXT DEFAULT '',
    comment_timestamp TIMESTAMP NOT NULL,
    video_id BIGINT NOT NULL,
    PRIMARY KEY (comment_id);
)

DROP SEQUENCE hibernate_sequence;

CREATE SEQUENCE hibernate_sequence;