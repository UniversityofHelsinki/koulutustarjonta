CREATE TABLE HAKU
(
  ID               VARCHAR2(300 CHAR),
  ID_HAKUKOHDE     VARCHAR2(300 CHAR),
  NIMI_FI          VARCHAR2(255 CHAR),
  NIMI_EN          VARCHAR2(255 CHAR),
  NIMI_SV          VARCHAR2(255 CHAR),
  HAKUTAPA         VARCHAR2(100 CHAR),
  HAKUKAUSI_VUOSI  INTEGER,
  HAKUKAUSI        VARCHAR2(1 CHAR),
  KOUL_ALK_VUOSI   INTEGER,
  KOUL_ALK_KAUSI   VARCHAR2(1 CHAR),
  JARJ_HAKULOMAKE  NUMBER(1),
  HAKULOMAKE_URL   VARCHAR2(200 CHAR)
)
;

CREATE TABLE HAKUAIKA
(
  ID                VARCHAR2(300 CHAR),
  ID_HAKU           VARCHAR2(300 CHAR),
  HAKUAIKA_NIMI     VARCHAR2(100 CHAR),
  HAKUAIKA_ALKUPVM  DATE,
  HAKUAIKA_LOPPUVM  DATE
)
;


CREATE TABLE HAKUKOHDE
(
  ID             VARCHAR2(300 CHAR),
  ID_HAKU        VARCHAR2(300 CHAR),
  NIMI_FI        VARCHAR2(500 CHAR),
  NIMI_EN        VARCHAR2(500 CHAR),
  NIMI_SV        VARCHAR2(500 CHAR),
  ALOITUSPAIKAT  INTEGER,
  SORAKUVAUS_FI  CLOB,
  SORAKUVAUS_EN  CLOB,
  SORAKUVAUS_SV  CLOB,
  LISATIEDOT_FI  CLOB,
  LISATIEDOT_EN  CLOB,
  LISATIEDOT_SV  CLOB,
  VALINTAPER_FI  CLOB,
  VALINTAPER_EN  CLOB,
  VALINTAPER_SV  CLOB,
  HAKUKELP_FI    CLOB,
  HAKUKELP_EN    CLOB,
  HAKUKELP_SV    CLOB
)
;

CREATE TABLE HAKUKOHDE_KOULUTUS
(
  ID_KOULUTUS   VARCHAR2(300 CHAR),
  ID_HAKUKOHDE  VARCHAR2(300 CHAR)
)
;


CREATE TABLE HAKUKOHDE_LIITE
(
  ID_HAKUKOHDE  VARCHAR2(300 CHAR),
  ID_LIITE      VARCHAR2(300 CHAR)
)
;

CREATE TABLE HAKUKOHDE_VALINTAKOE
(
  ID_HAKUKOHDE   VARCHAR2(300 CHAR),
  ID_VALINTAKOE  VARCHAR2(300 CHAR)
)
;


CREATE TABLE HAKU_MUOKKAAJA
(
  ID_HAKU          VARCHAR2(300 CHAR),
  ID_ORGANISAATIO  VARCHAR2(300 CHAR)
)
;


CREATE TABLE KOULUTUS
(
  ID                 VARCHAR2(300 CHAR),
  KOULUTUSKOODI_FI   VARCHAR2(500 CHAR),
  KOULUTUSKOODI_EN   VARCHAR2(500 CHAR),
  KOULUTUSKOODI_SV   VARCHAR2(500 CHAR),
  KOULUTUSASTE_FI    VARCHAR2(500 CHAR),
  KOULUTUSASTE_EN    VARCHAR2(500 CHAR),
  KOULUTUSASTE_SV    VARCHAR2(500 CHAR),
  KOULUTUSALA_FI     VARCHAR2(500 CHAR),
  KOULUTUSALA_EN     VARCHAR2(500 CHAR),
  KOULUTUSALA_SV     VARCHAR2(500 CHAR),
  OPINTOALA_FI       VARCHAR2(500 CHAR),
  OPINTOALA_EN       VARCHAR2(500 CHAR),
  OPINTOALA_SV       VARCHAR2(500 CHAR),
  TUTKINTO_FI        VARCHAR2(500 CHAR),
  TUTKINTO_EN        VARCHAR2(500 CHAR),
  TUTKINTO_SV        VARCHAR2(500 CHAR),
  TASO_FI            VARCHAR2(500 CHAR),
  TASO_EN            VARCHAR2(500 CHAR),
  TASO_SV            VARCHAR2(500 CHAR),
  KOMOOID            VARCHAR2(300 CHAR),
  ID_ORGANISAATIO    VARCHAR2(200 CHAR),
  ORGANISAATIO_NIMI  VARCHAR2(700 CHAR),
  NIMI_FI            VARCHAR2(500 CHAR),
  NIMI_EN            VARCHAR2(500 CHAR),
  NIMI_SV            VARCHAR2(500 CHAR),
  KUVAUS_FI          CLOB,
  KUVAUS_EN          CLOB,
  KUVAUS_SV          CLOB,
  MAHDOLLISUUDET_FI  CLOB,
  MAHDOLLISUUDET_EN  CLOB,
  MAHDOLLISUUDET_SV  CLOB,
  TAVOITTEET_FI      CLOB,
  TAVOITTEET_EN      CLOB,
  TAVOITTEET_SV      CLOB,
  KUVAUSTUTKINTO_FI  CLOB,
  KUVAUSTUTKINTO_EN  CLOB,
  KUVAUSTUTKINTO_SV  CLOB,
  TUTKPAINO_FI       CLOB,
  TUTKPAINO_EN       CLOB,
  TUTKPAINO_SV       CLOB,
  YHTEISTYO_FI       CLOB,
  YHTEISTYO_EN       CLOB,
  YHTEISTYO_SV       CLOB,
  LOPPUKOEVAATI_FI   CLOB,
  LOPPUKOEVAATI_EN   CLOB,
  LOPPUKOEVAATI_SV   CLOB,
  PAAAINEVAL_FI      CLOB,
  PAAAINEVAL_EN      CLOB,
  PAAAINEVAL_SV      CLOB,
  LISAT_OPKIEL_FI    CLOB,
  LISAT_OPKIEL_EN    CLOB,
  LISAT_OPKIEL_SV    CLOB,
  SIJTYO_FI          CLOB,
  SIJTYO_EN          CLOB,
  SIJTYO_SV          CLOB,
  KANSVAL_FI         CLOB,
  KANSVAL_EN         CLOB,
  KANSVAL_SV         CLOB,
  SUUNNI_KESTO       INTEGER,
  SUUNNI_TYYPPI_FI   VARCHAR2(30 CHAR),
  SUUNNI_TYYPPI_EN   VARCHAR2(30 CHAR),
  SUUNNI_TYYPPI_SV   VARCHAR2(30 CHAR),
  ALKAMISKAUSI       VARCHAR2(1 CHAR),
  ALKAMISVUOSI       INTEGER,
  OPINTOJENLAAJUUS   INTEGER,
  TUTKINTONIMIKE_FI  VARCHAR2(500 CHAR),
  TUTKINTONIMIKE_EN  VARCHAR2(500 CHAR),
  TUTKINTONIMIKE_SV  VARCHAR2(500 CHAR)
)
;


CREATE TABLE KOULUTUS_OPETUSKIELI
(
  ID_KOULUTUS     VARCHAR2(300 CHAR),
  ID_OPETUSKIELI  VARCHAR2(300 CHAR)
)
;

CREATE TABLE KOULUTUS_ORGANISAATIO
(
  ID_KOULUTUS      VARCHAR2(300 CHAR),
  ID_ORGANISAATIO  VARCHAR2(300 CHAR)
)
;


CREATE TABLE LIITE
(
  ID            VARCHAR2(300 CHAR),
  KIELI         VARCHAR2(2 CHAR),
  ERAPAIVA      DATE,
  KUVAUS_FI     CLOB,
  KUVAUS_EN     CLOB,
  KUVAUS_SV     CLOB,
  OSOITE        VARCHAR2(500 CHAR),
  POSTINUMERO   VARCHAR2(500 CHAR),
  PTOIMIPAIKKA  VARCHAR2(500 CHAR)
)
;


CREATE TABLE OPETUSKIELI
(
  ID      VARCHAR2(300 CHAR),
  KIELI   VARCHAR2(2 CHAR),
  SELITE  VARCHAR2(255 CHAR)
)
;


CREATE TABLE ORGANISAATIO
(
  ID                   VARCHAR2(300 CHAR),
  KUSTANNUKSET_FI      CLOB,
  KUSTANNUKSET_EN      CLOB,
  KUSTANNUKSET_SV      CLOB,
  NIMI_FI              VARCHAR2(255 CHAR),
  NIMI_EN              VARCHAR2(255 CHAR),
  NIMI_SV              VARCHAR2(255 CHAR),
  ID_YHTEYSTIETO       VARCHAR2(300 CHAR),
  KV_KOULOHJ_FI        CLOB,
  KV_KOULOHJ_EN        CLOB,
  KV_KOULOHJ_SV        CLOB,
  OPLIIKKUVUUS_FI      CLOB,
  OPLIIKKUVUUS_EN      CLOB,
  OPLIIKKUVUUS_SV      CLOB,
  OPPIMISYMPARISTO_FI  CLOB,
  OPPIMISYMPARISTO_EN  CLOB,
  OPPIMISYMPARISTO_SV  CLOB,
  ID_SOME              VARCHAR2(300 CHAR),
  YLEISKUVAUS_FI       CLOB,
  YLEISKUVAUS_EN       CLOB,
  YLEISKUVAUS_SV       CLOB,
  ID_HAKIJAPALVELUYT   VARCHAR2(300 CHAR)
)
;

CREATE TABLE SOME
(
  ID           VARCHAR2(300 CHAR),
  KIELI        VARCHAR2(2 CHAR),
  FACEBOOK     VARCHAR2(255 CHAR),
  GOOGLE_PLUS  VARCHAR2(255 CHAR),
  TWITTER      VARCHAR2(255 CHAR),
  LINKEDIN     VARCHAR2(255 CHAR)
)
;


CREATE TABLE VALINTAKOE
(
  ID         VARCHAR2(300 CHAR),
  TYYPPI_FI  VARCHAR2(255 CHAR),
  TYYPPI_EN  VARCHAR2(255 CHAR),
  TYYPPI_SV  VARCHAR2(255 CHAR),
  KUVAUS_FI  CLOB,
  KUVAUS_EN  CLOB,
  KUVAUS_SV  CLOB
)
;


CREATE TABLE VALINTAKOE_AK
(
  ID             VARCHAR2(300 CHAR),
  ID_VALINTAKOE  VARCHAR2(300 CHAR),
  KIELI          VARCHAR2(2 CHAR),
  ALKAA          DATE,
  LOPPUU         DATE,
  OSOITE         VARCHAR2(500 CHAR),
  POSTINUMERO    VARCHAR2(500 CHAR),
  PTOIMIPAIKKA   VARCHAR2(500 CHAR)
)
;


CREATE TABLE YHTEYSTIETO
(
  ID                       VARCHAR2(300 CHAR),
  WWW                      VARCHAR2(255 CHAR),
  PUHELIN                  VARCHAR2(255 CHAR),
  EMAIL                    VARCHAR2(255 CHAR),
  FAX                      VARCHAR2(255 CHAR),
  KIELI                    VARCHAR2(2 CHAR),
  KAYNTI_POSTINUMERO       VARCHAR2(255 CHAR),
  KAYNTI_OSOITE            VARCHAR2(255 CHAR),
  KAYNTI_POSTITOIMIPAIKKA  VARCHAR2(255 CHAR),
  POSTI_POSTINUMERO        VARCHAR2(255 CHAR),
  POSTI_OSOITE             VARCHAR2(255 CHAR),
  POSTI_POSTITOIMIPAIKKA   VARCHAR2(255 CHAR)
)
;

CREATE UNIQUE INDEX PK_HAKU ON HAKU
(ID)
;

CREATE UNIQUE INDEX PK_YHTEYSTIETO ON YHTEYSTIETO
(ID)
;

CREATE UNIQUE INDEX PK_HAKUKOHDE ON HAKUKOHDE
(ID)
;

CREATE UNIQUE INDEX HAKUKOHDE_VALINTAKOE_PK ON HAKUKOHDE_VALINTAKOE
(ID_VALINTAKOE, ID_HAKUKOHDE)
;

CREATE UNIQUE INDEX PK_KOULUTUS ON KOULUTUS
(ID)
;

CREATE UNIQUE INDEX KOULUTUS_OPETUSKIELI_PK ON KOULUTUS_OPETUSKIELI
(ID_OPETUSKIELI, ID_KOULUTUS)
;

CREATE UNIQUE INDEX PK_LIITE ON LIITE
(ID)
;

CREATE UNIQUE INDEX UQ_OPETUSKIELI ON OPETUSKIELI
(KIELI, ID)
;

CREATE UNIQUE INDEX OPETUSKIELI_PK ON OPETUSKIELI
(ID)
;

CREATE UNIQUE INDEX ID_ORGANISAATIO ON ORGANISAATIO
(ID)
;

CREATE UNIQUE INDEX UQ_SOME ON SOME
(KIELI, ID)
;

CREATE UNIQUE INDEX SOME_PK ON SOME
(ID)
;

CREATE UNIQUE INDEX PK_VALINTAKOE ON VALINTAKOE
(ID)
;

CREATE UNIQUE INDEX ID_VALKOE_AK ON VALINTAKOE_AK
(ID)
;

CREATE UNIQUE INDEX PK_HAKUAIKA ON HAKUAIKA
(ID)
;

--ALTER TABLE HAKU ADD (
--  CONSTRAINT PK_HAKU
--  PRIMARY KEY
--  (ID)
--  USING INDEX PK_HAKU
--  );
--
--
--ALTER TABLE HAKUAIKA ADD (
--  CONSTRAINT PK_HAKUAIKA
--  PRIMARY KEY
--  (ID)
--  USING INDEX PK_HAKUAIKA
--  );
--
--
--ALTER TABLE HAKUKOHDE ADD (
--  CONSTRAINT PK_HAKUKOHDE
--  PRIMARY KEY
--  (ID)
--  USING INDEX PK_HAKUKOHDE
--  );
--
--
--ALTER TABLE HAKUKOHDE_VALINTAKOE ADD (
--  CONSTRAINT HAKUKOHDE_VALINTAKOE_PK
--  PRIMARY KEY
--  (ID_VALINTAKOE, ID_HAKUKOHDE)
--  USING INDEX HAKUKOHDE_VALINTAKOE_PK
--  );
--
--
--ALTER TABLE KOULUTUS ADD (
--  CONSTRAINT PK_KOULUTUS
--  PRIMARY KEY
--  (ID)
--  USING INDEX PK_KOULUTUS
--  );
--
--
--ALTER TABLE KOULUTUS_OPETUSKIELI ADD (
--  CONSTRAINT KOULUTUS_OPETUSKIELI_PK
--  PRIMARY KEY
--  (ID_OPETUSKIELI, ID_KOULUTUS)
--  USING INDEX KOULUTUS_OPETUSKIELI_PK
--  );
--
--
--ALTER TABLE LIITE ADD (
--  CONSTRAINT PK_LIITE
--  PRIMARY KEY
--  (ID)
--  USING INDEX PK_LIITE
--  );
--
--
--ALTER TABLE OPETUSKIELI ADD (
--  CONSTRAINT OPETUSKIELI_PK
--  PRIMARY KEY
--  (ID)
--  USING INDEX OPETUSKIELI_PK
--  );
--
--ALTER TABLE OPETUSKIELI ADD (
--  CONSTRAINT UQ_OPETUSKIELI
--  UNIQUE (KIELI, ID)
--  USING INDEX UQ_OPETUSKIELI
--  );
--
--
--ALTER TABLE ORGANISAATIO ADD (
--  CONSTRAINT ID_ORGANISAATIO
--  PRIMARY KEY
--  (ID)
--  USING INDEX ID_ORGANISAATIO
--  );
--
--
--ALTER TABLE SOME ADD (
--  CONSTRAINT SOME_PK
--  PRIMARY KEY
--  (ID)
--  USING INDEX SOME_PK
--  );
--
--ALTER TABLE SOME ADD (
--  CONSTRAINT UQ_SOME
--  UNIQUE (KIELI, ID)
--  USING INDEX UQ_SOME
--  );
--
--
--ALTER TABLE VALINTAKOE ADD (
--  CONSTRAINT PK_VALINTAKOE
--  PRIMARY KEY
--  (ID)
--  USING INDEX PK_VALINTAKOE
--  );
--
--
--ALTER TABLE VALINTAKOE_AK ADD (
--  CONSTRAINT ID_VALKOE_AK
--  PRIMARY KEY
--  (ID)
--  USING INDEX ID_VALKOE_AK
--  );
--
--
--ALTER TABLE YHTEYSTIETO ADD (
--  CONSTRAINT PK_YHTEYSTIETO
--  PRIMARY KEY
--  (ID)
--  USING INDEX PK_YHTEYSTIETO
--  );


--ALTER TABLE HAKU ADD (
--  CONSTRAINT FK_HAKU_HAKUKOHDE
--  FOREIGN KEY (ID_HAKUKOHDE)
--  REFERENCES HAKUKOHDE (ID)
--  );
--
--
--ALTER TABLE HAKUAIKA ADD (
--  CONSTRAINT HAKUAIKA_R01
--  FOREIGN KEY (ID_HAKU)
--  REFERENCES HAKU (ID)
--  );
--
--
--ALTER TABLE HAKUKOHDE ADD (
--  CONSTRAINT HAKUKOHDE_R01
--  FOREIGN KEY (ID_HAKU)
--  REFERENCES HAKU (ID)
--  );
--
--
--ALTER TABLE HAKUKOHDE_KOULUTUS ADD (
--  CONSTRAINT HAKUKOHDE_KOULUTUS_R01
--  FOREIGN KEY (ID_KOULUTUS)
--  REFERENCES KOULUTUS (ID)
--  );
--
--ALTER TABLE HAKUKOHDE_KOULUTUS ADD (
--  CONSTRAINT HAKUKOHDE_KOULUTUS_R02
--  FOREIGN KEY (ID_HAKUKOHDE)
--  REFERENCES HAKUKOHDE (ID)
--  );
--
--
--ALTER TABLE HAKUKOHDE_LIITE ADD (
--  CONSTRAINT HAKUKOHDE_LIITE_R01
--  FOREIGN KEY (ID_HAKUKOHDE)
--  REFERENCES HAKUKOHDE (ID)
--  );
--
--ALTER TABLE HAKUKOHDE_LIITE ADD (
--  CONSTRAINT HAKUKOHDE_LIITE_R02
--  FOREIGN KEY (ID_LIITE)
--  REFERENCES LIITE (ID)
--  );
--
--
--ALTER TABLE HAKUKOHDE_VALINTAKOE ADD (
--  CONSTRAINT HAKUKOHDE_VALINTAKOE_R01
--  FOREIGN KEY (ID_HAKUKOHDE)
--  REFERENCES HAKUKOHDE (ID)
--  );
--
--ALTER TABLE HAKUKOHDE_VALINTAKOE ADD (
--  CONSTRAINT HAKUKOHDE_VALINTAKOE_R02
--  FOREIGN KEY (ID_VALINTAKOE)
--  REFERENCES VALINTAKOE (ID)
--  );
--
--
--ALTER TABLE HAKU_MUOKKAAJA ADD (
--  CONSTRAINT FK_HM_HAKU
--  FOREIGN KEY (ID_HAKU)
--  REFERENCES HAKU (ID)
--  );
--
--ALTER TABLE HAKU_MUOKKAAJA ADD (
--  CONSTRAINT FK_HM_MUOKKAAJA
--  FOREIGN KEY (ID_ORGANISAATIO)
--  REFERENCES ORGANISAATIO (ID)
--  );
--
--
--ALTER TABLE KOULUTUS ADD (
--  CONSTRAINT KOULUTUS_R01
--  FOREIGN KEY (ID_ORGANISAATIO)
--  REFERENCES ORGANISAATIO (ID)
--  );
--
--
--ALTER TABLE KOULUTUS_OPETUSKIELI ADD (
--  CONSTRAINT KOULUTUS_OPETUSKIELI_R01
--  FOREIGN KEY (ID_KOULUTUS)
--  REFERENCES KOULUTUS (ID)
--  );
--
--ALTER TABLE KOULUTUS_OPETUSKIELI ADD (
--  CONSTRAINT KOULUTUS_OPETUSKIELI_R02
--  FOREIGN KEY (ID_OPETUSKIELI)
--  REFERENCES OPETUSKIELI (ID)
--  );
--
--
--ALTER TABLE KOULUTUS_ORGANISAATIO ADD (
--  CONSTRAINT KOULUTUS_ORGANISAATIO_R01
--  FOREIGN KEY (ID_ORGANISAATIO)
--  REFERENCES ORGANISAATIO (ID)
--  );
--
--
--ALTER TABLE ORGANISAATIO ADD (
--  CONSTRAINT ORGANISAATIO_R01
--  FOREIGN KEY (ID_YHTEYSTIETO)
--  REFERENCES YHTEYSTIETO (ID)
--  );
--
--ALTER TABLE ORGANISAATIO ADD (
--  CONSTRAINT ORGANISAATIO_R02
--  FOREIGN KEY (ID_HAKIJAPALVELUYT)
--  REFERENCES YHTEYSTIETO (ID)
--  );
--
--ALTER TABLE ORGANISAATIO ADD (
--  CONSTRAINT ORGANISAATIO_R03
--  FOREIGN KEY (ID_SOME)
--  REFERENCES SOME (ID)
--  );
--
--
--ALTER TABLE VALINTAKOE_AK ADD (
--  CONSTRAINT VALINTAKOE_AK_R01
--  FOREIGN KEY (ID_VALINTAKOE)
--  REFERENCES VALINTAKOE (ID)
--  );
