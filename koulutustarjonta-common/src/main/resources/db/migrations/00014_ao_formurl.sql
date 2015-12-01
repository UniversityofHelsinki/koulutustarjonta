--liquibase formatted sql

--changeset lauri:14
ALTER TABLE HAKUKOHDE

  ADD (
    HAKULOMAKE_URL VARCHAR2(300 CHAR)
);

ALTER TABLE HAKU
    ADD (
        CONSTRAINT HAKU_R01
        CHECK
        (
            JARJ_HAKULOMAKE IN ('0', '1')
        )
    );
