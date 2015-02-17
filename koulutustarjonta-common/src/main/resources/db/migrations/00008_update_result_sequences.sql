-- liquibase formatted sql

-- changeset sebastian:8
CREATE SEQUENCE PAIVITYS_SEQ;

-- extra changeset to allow PL/SQL
-- changeset sebastian:8.1 endDelimiter:/ splitStatements:true
CREATE OR REPLACE TRIGGER PAIVITYS_TRIG
BEFORE INSERT ON PAIVITYS
FOR EACH ROW
WHEN (new.ID IS NULL)
  BEGIN
    SELECT PAIVITYS_SEQ.NEXTVAL
    INTO   :new.ID
    FROM   dual;
  END;
/
