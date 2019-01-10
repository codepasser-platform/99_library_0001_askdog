-- user related tables
-- user
CREATE TABLE ad_user
(
  uuid VARCHAR(255) NOT NULL,
  name VARCHAR(20) NOT NULL,
  password VARCHAR(255),
  phone_number VARCHAR(20),
  email VARCHAR(80),
  type VARCHAR(20) NOT NULL,
  statuses VARCHAR(255),
  last_access_time TIMESTAMP WITH TIME ZONE,
  registration_time TIMESTAMP WITH TIME ZONE,
  PRIMARY KEY (uuid),
  CONSTRAINT "UK_user_name" UNIQUE (name),
  CONSTRAINT "UK_user_phone_number" UNIQUE (phone_number),
  CONSTRAINT "UK_user_email" UNIQUE (email)
);

-- external user
CREATE TABLE ad_external_user
(
  uuid VARCHAR(255) NOT NULL,
  external_user_id VARCHAR(255) NOT NULL,
  provider VARCHAR(20) NOT NULL,
  bind_user VARCHAR(255) NOT NULL,
  PRIMARY KEY (uuid),
  CONSTRAINT "FK_external_bind_user_user_id" FOREIGN KEY (bind_user)
    REFERENCES ad_user (uuid)
      ON UPDATE NO ACTION
      ON DELETE CASCADE
);

-- label
CREATE TABLE ad_label
(
  uuid VARCHAR(255) NOT NULL,
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY (uuid),
  CONSTRAINT "UK_label_name" UNIQUE (name)
);

-- owner
CREATE TABLE ad_question
(
  uuid VARCHAR(255) NOT NULL,
  content VARCHAR(5028) NOT NULL,
  creation_time TIMESTAMP WITH TIME ZONE NOT NULL,
  language VARCHAR(255) NOT NULL,
  state VARCHAR(255) NOT NULL,
  subject VARCHAR(150) NOT NULL,
  author VARCHAR(255) NOT NULL,
  PRIMARY KEY (uuid),
  CONSTRAINT "FK_question_author_user_id" FOREIGN KEY (author)
    REFERENCES ad_user (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
  ON DELETE NO ACTION
);

-- relationship of owner and label
CREATE TABLE ad_question_label
(
  owner VARCHAR(255) NOT NULL,
  label VARCHAR(255) NOT NULL,
  CONSTRAINT "FK_question_label_question_id" FOREIGN KEY (owner)
    REFERENCES ad_question (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION,
  CONSTRAINT "FK_question_label_label_id" FOREIGN KEY (label)
    REFERENCES ad_label (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
);

-- favorite questions
CREATE TABLE ad_question_favorite
(
  owner VARCHAR(255) NOT NULL,
  owner VARCHAR(255) NOT NULL,
  CONSTRAINT "FK_question_favorite_user_id" FOREIGN KEY (owner)
    REFERENCES ad_user (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION,
  CONSTRAINT "FK_question_favorite_question_id" FOREIGN KEY (owner)
    REFERENCES ad_question (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
);

-- answer
CREATE TABLE ad_answer
(
  uuid VARCHAR(255) NOT NULL,
  content VARCHAR(5028) NOT NULL,
  creation_time TIMESTAMP WITH TIME ZONE NOT NULL,
  language VARCHAR(255) NOT NULL,
  owner VARCHAR(255) NOT NULL,
  answerer VARCHAR(255) NOT NULL,
  PRIMARY KEY (uuid),
  CONSTRAINT "FK_answer_answerer_user_id" FOREIGN KEY (answerer)
    REFERENCES ad_user (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION,
  CONSTRAINT "FK_answer_question_question_id" FOREIGN KEY (owner)
    REFERENCES ad_question(uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
);

-- owner comment
CREATE TABLE ad_question_comment
(
  uuid VARCHAR(255) NOT NULL,
  content VARCHAR(200) NOT NULL,
  creation_time TIMESTAMP WITH TIME ZONE NOT NULL,
  commenter VARCHAR(255) NOT NULL,
  referred_comment VARCHAR(255),
  owner VARCHAR(255) NOT NULL,
  PRIMARY KEY (uuid),
  CONSTRAINT "FK_question_comment_referred_comment_question_comment_id" FOREIGN KEY (referred_comment)
    REFERENCES ad_question_comment (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION,
  CONSTRAINT "FK_question_comment_question_question_id" FOREIGN KEY (owner)
    REFERENCES ad_question (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION,
  CONSTRAINT "FK_question_comment_commenter_user_id" FOREIGN KEY (commenter)
    REFERENCES ad_user (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
);

-- answer comment
CREATE TABLE ad_answer_comment
(
  uuid VARCHAR(255) NOT NULL,
  content VARCHAR(200) NOT NULL,
  creation_time TIMESTAMP WITH TIME ZONE NOT NULL,
  commenter VARCHAR(255) NOT NULL,
  referred_comment VARCHAR(255),
  answer VARCHAR(255) NOT NULL,
  PRIMARY KEY (uuid),
  CONSTRAINT "FK_answer_comment_referred_comment_answer_comment_id" FOREIGN KEY (referred_comment)
    REFERENCES ad_answer_comment (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION,
  CONSTRAINT "FK_answer_comment_answer_answer_id" FOREIGN KEY (answer)
    REFERENCES ad_answer (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION,
  CONSTRAINT "FK_answer_comment_commenter_user_id" FOREIGN KEY (commenter)
    REFERENCES ad_user (uuid) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
);

-- common tables
-- template
CREATE TABLE ad_template
(
  uuid VARCHAR(255) NOT NULL,
  content VARCHAR(2000) NOT NULL,
  creation_time TIMESTAMP WITH TIME ZONE NOT NULL,
  language VARCHAR(255) NOT NULL,
  last_modify_time TIMESTAMP WITH TIME ZONE,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (uuid),
  CONSTRAINT "UK_template_name_language" UNIQUE (name, language)
);

-- oauth related tables
-- used in tests that use HSQL
CREATE TABLE oauth_client_details
(
  client_id VARCHAR(255) NOT NULL,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255),
  PRIMARY KEY (client_id)
);

CREATE TABLE oauth_client_token (
  token_id VARCHAR(255),
  token TEXT,
  authentication_id VARCHAR(255) NOT NULL,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  PRIMARY KEY (authentication_id)
);

CREATE TABLE oauth_access_token (
  token_id VARCHAR(255),
  token TEXT,
  authentication_id VARCHAR(255) NOT NULL,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication TEXT,
  refresh_token VARCHAR(255),
  PRIMARY KEY (authentication_id)
);

CREATE TABLE oauth_refresh_token (
  token_id VARCHAR(255),
  token TEXT,
  authentication TEXT
);

CREATE TABLE oauth_code (
  code VARCHAR(255),
  authentication TEXT
);

CREATE TABLE oauth_approvals (
  userId VARCHAR(255),
  clientId VARCHAR(255),
  scope VARCHAR(255),
  status VARCHAR(10),
  expiresAt TIMESTAMP WITHOUT TIME ZONE,
  lastModifiedAt TIMESTAMP WITHOUT TIME ZONE
);