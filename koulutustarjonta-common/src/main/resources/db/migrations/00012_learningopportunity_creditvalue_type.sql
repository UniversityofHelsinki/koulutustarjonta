--liquibase formatted sql

--changeset ian:12

alter TABLE koulutus ADD (
  temp varchar2 (300 CHAR)
);

update
  koulutus
set
  temp = laajuus;

alter table
  koulutus
drop COLUMN
  laajuus;

alter table
  koulutus
rename COLUMN
  temp
TO
  laajuus;
