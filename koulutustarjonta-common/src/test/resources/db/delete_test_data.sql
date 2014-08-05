-- koulutus
DELETE FROM KOULUTUS_OPETUSKIELI
WHERE id_koulutus = '1.2.3';

DELETE FROM KOULUTUS
WHERE id = '1.2.3';

-- hakukohde
DELETE FROM valintakoe_ak
WHERE id = 'ajankohta_id1';

DELETE FROM valintakoe
WHERE id = 'valintakoe_id1';

DELETE FROM hakukohde
WHERE id = '2.1.2.3';
