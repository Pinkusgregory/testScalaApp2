-- Chapters schema

-- !Ups

CREATE TABLE chapter
(
  id_chapter character varying NOT NULL,
  short_name character varying,
  full_name character varying,
  id_upper_chapter character varying,
  CONSTRAINT chapter_pkey PRIMARY KEY (id_chapter)
)

-- !Downs

DROP TABLE chapter;