--liquibase formatted sql

--changeset sebastian:6
CREATE TABLE PAIVITYS
(
  ID              INTEGER,
  ALOITETTU       TIMESTAMP,
  TILA            VARCHAR2(500 CHAR),
  VIRHEET         CLOB
)
LOGGING
NOCOMPRESS
NOCACHE
NOPARALLEL
MONITORING;
