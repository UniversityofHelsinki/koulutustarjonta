--liquibase formatted sql

--changeset ian:12

alter table
  koulutus
modify
(
   laajuus    varchar2(300)
);