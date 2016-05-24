--liquibase formatted sql

--changeset jonimake:22

DROP TABLE KOULUTUS_YHTEYSTIETO_KIELI;

--changeset jonimake:22.1

ALTER TABLE KOULUTUS_YHTEYSTIETO
ADD (KIELI VARCHAR2(100 CHAR));

