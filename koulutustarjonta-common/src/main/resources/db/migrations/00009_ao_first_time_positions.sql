--liquibase formatted sql

--changeset ian:9
ALTER TABLE HAKUKOHDE

  ADD (
    ENSIKERTALAISTEN_ALOITUSPAIKAT INTEGER
)