--liquibase formatted sql

--changeset jonimake:21

ALTER TABLE KOULUTUS_YHTEYSTIETO_KIELI DROP CONSTRAINT FK_KOULUTUS_YHTEYSTIETO;
ALTER TABLE KOULUTUS_YHTEYSTIETO_KIELI
ADD CONSTRAINT FK_KOULUTUS_YHTEYSTIETO
FOREIGN KEY (KOULUTUS_YHTEYSTIETO_ID) REFERENCES KOULUTUS_YHTEYSTIETO (ID) ON DELETE CASCADE;