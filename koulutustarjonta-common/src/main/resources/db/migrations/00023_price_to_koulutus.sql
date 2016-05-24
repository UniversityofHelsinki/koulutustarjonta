--liquibase formatted sql

--changeset jonimake:23

ALTER TABLE KOULUTUS
ADD (HINTA NUMBER(9,2));