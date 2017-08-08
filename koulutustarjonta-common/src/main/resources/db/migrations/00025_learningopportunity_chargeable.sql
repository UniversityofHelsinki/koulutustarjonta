--liquibase formatted sql

--changeset pasi.viertola:25
ALTER TABLE KOULUTUS
  ADD (
  MAKSULLISUUS_FI CLOB,
  MAKSULLISUUS_SV CLOB,
  MAKSULLISUUS_EN CLOB
  )
