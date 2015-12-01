--liquibase formatted sql

--changeset ian:10

ALTER TABLE ORGANISAATIO
 DROP (
   FACEBOOK_FI,
   FACEBOOK_SV,
   FACEBOOK_EN,
   GOOGLE_PLUS_FI,
   GOOGLE_PLUS_SV,
   GOOGLE_PLUS_EN,
   LINKEDIN_FI,
   LINKEDIN_SV,
   LINKEDIN_EN,
   TWITTER_FI,
   TWITTER_SV,
   TWITTER_EN
 );

ALTER TABLE SOME
 DROP (
  KIELI,
  FACEBOOK,
  GOOGLE_PLUS,
  TWITTER,
  LINKEDIN
);

 ALTER TABLE SOME
  ADD (
   ID_ORGANISAATIO    VARCHAR2(300 CHAR),
   FACEBOOK_FI        VARCHAR2(255 CHAR),
   FACEBOOK_SV        VARCHAR2(255 CHAR),
   FACEBOOK_EN        VARCHAR2(255 CHAR),
   GOOGLE_PLUS_FI     VARCHAR2(255 CHAR),
   GOOGLE_PLUS_SV     VARCHAR2(255 CHAR),
   GOOGLE_PLUS_EN     VARCHAR2(255 CHAR),
   LINKEDIN_FI        VARCHAR2(255 CHAR),
   LINKEDIN_SV        VARCHAR2(255 CHAR),
   LINKEDIN_EN        VARCHAR2(255 CHAR),
   TWITTER_FI         VARCHAR2(255 CHAR),
   TWITTER_SV         VARCHAR2(255 CHAR),
   TWITTER_EN         VARCHAR2(255 CHAR),
   SOME_OTHER_FI      VARCHAR2(255 CHAR),
   SOME_OTHER_SV      VARCHAR2(255 CHAR),
   SOME_OTHER_EN      VARCHAR2(255 CHAR),
   INSTAGRAM_FI       VARCHAR2(255 CHAR),
   INSTAGRAM_SV       VARCHAR2(255 CHAR),
   INSTAGRAM_EN       VARCHAR2(255 CHAR),
   YOUTUBE_FI         VARCHAR2(255 CHAR),
   YOUTUBE_SV         VARCHAR2(255 CHAR),
   YOUTUBE_EN         VARCHAR2(255 CHAR)
);