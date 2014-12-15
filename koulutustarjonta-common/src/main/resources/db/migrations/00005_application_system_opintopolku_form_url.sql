--liquibase formatted sql

--changeset sebastian:5
ALTER TABLE HAKU
  ADD (
    OPINTOPOLKU_HAKULOMAKE_URL VARCHAR2(300 CHAR)
)