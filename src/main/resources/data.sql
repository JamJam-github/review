drop table if exists triple.review_m;

CREATE TABLE review_m (
  review_id VARCHAR(45) NOT NULL,
  user_id VARCHAR(45) NULL,
  place_id VARCHAR(45) NULL,
  content VARCHAR(45) NULL,
  point INT NULL DEFAULT 0,
  del_yn VARCHAR(1) NULL DEFAULT 'N',
  PRIMARY KEY (review_id));

drop table if exists review_h;

CREATE TABLE review_h (
  review_id VARCHAR(45) NOT NULL,
  seq INT NOT NULL,
  action VARCHAR(10) NULL,
  user_id VARCHAR(45) NULL,
  place_id VARCHAR(45) NULL,
  content VARCHAR(45) NULL,
  point INT NULL DEFAULT 0,
  PRIMARY KEY (review_id, seq));

drop table if exists user;

CREATE TABLE user (
  user_id VARCHAR(45) NOT NULL,
  tot_point INT NULL DEFAULT 0,
  PRIMARY KEY (user_id));

drop table if exists attach;

CREATE TABLE attach (
  REVIEW_ID VARCHAR(45) NOT NULL,
  ATTACHED_PHOTOID VARCHAR(45) NOT NULL,
  PRIMARY KEY (REVIEW_ID, ATTACHED_PHOTOID));