--liquibase formatted sql

--changeset pasi.viertola:26

ALTER TABLE LIITE
ADD (VASTAANOTTAJA VARCHAR2(500 CHAR));

