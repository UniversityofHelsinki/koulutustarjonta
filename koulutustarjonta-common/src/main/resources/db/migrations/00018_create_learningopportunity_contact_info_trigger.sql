--liquibase formatted sql

--changeset jonimake:18 endDelimiter:/ splitStatements:true

CREATE OR REPLACE TRIGGER TRG_KTIETO_YHTEYSTIETO_ID
  BEFORE INSERT ON KOULUTUS_YHTEYSTIETO
  FOR EACH ROW
  WHEN (new.ID IS NULL)
  BEGIN
    SELECT KOULUTUS_YHTEYSTIETO_ID_SEQ.nextval
      INTO :new.ID
      FROM dual;
  END;
/