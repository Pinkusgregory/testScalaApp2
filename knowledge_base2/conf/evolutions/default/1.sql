-- Articles schema

-- !Ups

CREATE TABLE article
(
  id_article character varying NOT NULL,
  short_name character varying,
  full_name character varying,
  text character varying,
  id_upper_chapter character varying,
  CONSTRAINT article_pkey PRIMARY KEY (id_article)
)

-- !Downs

DROP TABLE article;