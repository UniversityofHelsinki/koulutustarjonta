-- koulutus
INSERT INTO KOULUTUS
            (id,  tutkintonimike_fi, tutkintonimike_sv, tutkintonimike_en,
            opintoala_fi, opintoala_sv, opintoala_en,
            tutkintoohjelma_fi, tutkintoohjelma_sv, tutkintoohjelma_en,
            alkamisvuosi, alkamiskausi_fi, alkamiskausi_sv, alkamiskausi_en,
            suunni_kesto, suunni_tyyppi_fi, suunni_tyyppi_sv, suunni_tyyppi_en,
            laajuus, laajuus_tyyppi_fi, laajuus_tyyppi_sv, laajuus_tyyppi_en,
            rakenne_fi, rakenne_sv, rakenne_en,
            tavoitteet_fi, tavoitteet_sv, tavoitteet_en,
            mahdollisuudet_fi, mahdollisuudet_sv, mahdollisuudet_en,
            patevyys_fi, patevyys_sv, patevyys_en,
            lisat_opkiel_fi, lisat_opkiel_sv, lisat_opkiel_en,
            yhteistyo_fi, yhteistyo_sv, yhteistyo_en,
            paaaineval_fi, paaaineval_sv, paaaineval_en,
            kansval_fi, kansval_sv, kansval_en,
            sijtyo_fi, sijtyo_sv, sijtyo_en,
            sisalto_fi, sisalto_sv, sisalto_en,
            tutkpaino_fi, tutkpaino_sv, tutkpaino_en,
            opinnaytetyo_fi, opinnaytetyo_sv, opinnaytetyo_en)
VALUES ('1.2.3', 'tutkintonimike fi', 'tutkintonimike sv', 'tutkintonimike en',
'opintoala fi', 'opintoala sv', 'opintoala en',
'tutkintoohjelma fi', 'tutkintoohjelma sv', 'tutkintoohjelma en',
2015, 'alkamiskausi fi', 'alkamiskausi sv', 'alkamiskausi en',
3, 'suunni tyyppi fi', 'suunni tyyppi sv', 'suunni tyyppi en',
120, 'laajuus tyyppi fi', 'laajuus tyyppi sv', 'laajuus tyyppi en',
'rakenne fi', 'rakenne sv', 'rakenne en',
'tavoitteet fi', 'tavoitteet sv', 'tavoitteet en',
'mahdollisuudet fi', 'mahdollisuudet sv', 'mahdollisuudet en',
'patevyys fi', 'patevyys sv', 'patevyys en',
'lisatietoja kielista fi', 'lisatietoja kielista sv', 'lisatietoja kielista en',
'yhteistyo fi', 'yhteistyo sv', 'yhteistyo en',
'paaaineen valinta fi', 'paaaineen valinta sv', 'paaaineen valinta en',
'kansainvalinen toiminta fi', 'kansainvalinen toiminta sv', 'kansainvalinen toiminta en',
'sijoittuminen tyoelamaan fi', 'sijoittuminen tyoelamaan sv', 'sijoittuminen tyoelamaan en',
'sisalto fi', 'sisalto sv', 'sisalto en',
'tutkimuksen painotus fi', 'tutkimuksen painotus sv', 'tutkimuksen painotus en',
'opinnaytetyo fi', 'opinnaytetyo sv', 'opinnaytetyo en');

MERGE INTO opetuskieli USING dual on ( id = 'fi' )
WHEN NOT MATCHED THEN INSERT (id, kieli, selite_fi, selite_sv, selite_en)
VALUES ('fi', 'fi', 'suomi', 'finska', 'Finnish');

MERGE INTO opetuskieli USING dual on ( id = 'sv' )
WHEN NOT MATCHED THEN INSERT (id, kieli, selite_fi, selite_sv, selite_en)
VALUES ('sv', 'sv', 'ruotsi', 'svenska', 'Swedish');

INSERT INTO KOULUTUS_OPETUSKIELI (id_koulutus, id_opetuskieli)
VALUES ('1.2.3', 'fi');
INSERT INTO KOULUTUS_OPETUSKIELI (id_koulutus, id_opetuskieli)
VALUES ('1.2.3', 'sv');

-- haku
INSERT INTO haku
(id, nimi_fi, nimi_sv, nimi_en, hakutapa_fi, hakutapa_sv, hakutapa_en,
hakukausi_vuosi, hakukausi_arvo, hakukausi_fi, hakukausi_sv, hakukausi_en,
koul_alk_vuosi, koul_alk_kausi_arvo, koul_alk_kausi_fi, koul_alk_kausi_sv,
koul_alk_kausi_en, hakulomake_url)
VALUES
('haku_id1', 'haku nimi fi', 'haku nimi sv', 'haku nimi en', 'hakutapa fi', 'hakutapa sv', 'hakutapa en',
2015, 'K', 'hakukausi fi', 'hakukausi sv', 'hakukausi en',
2016, 'S', 'koul alk kausi fi', 'koul alk kausi sv',
'koul alk kausi en', 'hakulomake url');

INSERT INTO hakuaika
(id, nimi, alkaa, loppuu, id_haku)
VALUES
('hakuaika_id1', 'hakuaika nimi',
TO_DATE('2015/01/01 15:00:00', 'yyyy/mm/dd hh24:mi:ss'),
TO_DATE('2015/02/01 15:00:00', 'yyyy/mm/dd hh24:mi:ss'),
'haku_id1');

-- hakukohde
INSERT INTO hakukohde
            (id, nimi_fi, nimi_sv, nimi_en, aloituspaikat,
            sorakuvaus_fi, sorakuvaus_sv, sorakuvaus_en,
            lisatiedot_fi, lisatiedot_sv, lisatiedot_en,
            valintaper_fi, valintaper_sv, valintaper_en,
            hakukelp_kuvaus_fi, hakukelp_kuvaus_sv, hakukelp_kuvaus_en)
VALUES ('hakukohde_id1', 'nimi fi', 'nimi sv', 'nimi en', 11, 'sora fi', 'sora sv', 'sora en',
'lisatiedot fi', 'lisatiedot sv', 'lisatiedot en',
'valintaper fi', 'valintaper sv', 'valintaper en',
'kuvaus fi', 'kuvaus sv', 'kuvaus en');

INSERT INTO valintakoe
            (id, id_hakukohde, kieli, tyyppi, kuvaus)
VALUES ('valintakoe_id1', 'hakukohde_id1', 'fi', 'koetyyppi', 'kokeen kuvaus');

INSERT INTO valintakoe_ak
(id, id_valintakoe, alkaa, loppuu, kuvaus, osoite, postinumero, ptoimipaikka)
VALUES
('ajankohta_id1', 'valintakoe_id1',
TO_DATE('2015/01/03 15:00:00', 'yyyy/mm/dd hh24:mi:ss'),
TO_DATE('2015/01/03 18:00:00', 'yyyy/mm/dd hh24:mi:ss'),
'valintakoeajankohta kuvaus', 'katuosoite', 'postinumero',
'postitoimipaikka');

INSERT INTO liite
(id, id_hakukohde, kieli, nimi, erapaiva, kuvaus, osoite, postinumero, ptoimipaikka)
VALUES
('liite_id1', 'hakukohde_id1', 'fi', 'liite nimi',
TO_DATE('2015/02/01 12:00:00', 'yyyy/mm/dd hh24:mi:ss'),
'liite kuvaus', 'liite katuosoite', 'liite postinumero', 'liite ptoimipaikka');

INSERT INTO hakukelp
(id, id_hakukohde, kuvaus_fi, kuvaus_sv, kuvaus_en)
VALUES
(999999, 'hakukohde_id1', 'hakukelp fi', 'hakukelp sv', 'hakukelp en');
