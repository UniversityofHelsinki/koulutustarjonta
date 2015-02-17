--liquibase formatted sql

--changeset sebastian:7
ALTER TABLE PAIVITYS ADD (
  CONSTRAINT PK_PAIVITYS PRIMARY KEY (ID)
ENABLE VALIDATE);
