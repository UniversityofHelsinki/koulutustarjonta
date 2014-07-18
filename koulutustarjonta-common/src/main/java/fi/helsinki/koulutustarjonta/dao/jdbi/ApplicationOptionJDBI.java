package fi.helsinki.koulutustarjonta.dao.jdbi;

import fi.helsinki.koulutustarjonta.dao.binder.BindApplicationOption;
import fi.helsinki.koulutustarjonta.dao.binder.BindAttachment;
import fi.helsinki.koulutustarjonta.dao.binder.BindExam;
import fi.helsinki.koulutustarjonta.dao.binder.BindExamEvent;
import fi.helsinki.koulutustarjonta.dao.mapper.ApplicationOptionJoinRowMapper;
import fi.helsinki.koulutustarjonta.dao.util.ApplicationOptionJoinRow;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.Attachment;
import fi.helsinki.koulutustarjonta.domain.Exam;
import fi.helsinki.koulutustarjonta.domain.ExamEvent;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
public interface ApplicationOptionJDBI {

    @SqlUpdate("INSERT INTO hakukohde" +
            "(id, nimi_fi, nimi_sv, nimi_en, aloituspaikat, hakukelp_fi, hakukelp_sv, hakukelp_en, " +
            "lisatiedot_fi, lisatiedot_sv, lisatiedot_en, valintaper_fi, valintaper_sv, valintaper_en, " +
            "sorakuvaus_fi, sorakuvaus_sv, sorakuvaus_en) " +
            "VALUES (:id, :nimi_fi, :nimi_sv, :nimi_en, :aloituspaikat, :hakukelp_fi, :hakukelp_sv, :hakukelp_en, " +
            ":lisatiedot_fi, :lisatiedot_sv, :lisatiedot_en, :valintaper_fi, :valintaper_sv, :valintaper_en, " +
            ":sorakuvaus_fi, :sorakuvaus_sv, :sorakuvaus_en)")
    void insert(@BindApplicationOption ApplicationOption applicationOption);


    @SqlUpdate("UPDATE hakukohde " +
            "SET nimi_fi=:nimi_fi, nimi_sv=:nimi_sv, nimi_en=:nimi_en, aloituspaikat=:aloituspaikat, " +
            " hakukelp_fi=:hakukelp_fi, hakukelp_sv=:hakukelp_sv, hakukelp_en=:hakukelp_en, " +
            "lisatiedot_fi=:lisatiedot_fi, lisatiedot_sv=:lisatiedot_sv, lisatiedot_en=:lisatiedot_en, " +
            "valintaper_fi=:valintaper_fi, valintaper_sv=:valintaper_sv, valintaper_en=:valintaper_en, " +
            "sorakuvaus_fi=:sorakuvaus_fi, sorakuvaus_sv=:sorakuvaus_sv, sorakuvaus_en=:sorakuvaus_en " +
            "WHERE id=:id")
    int update(@BindApplicationOption ApplicationOption applicationOption);

    @SqlUpdate("MERGE INTO hakukohde USING dual ON ( id=:id ) " +
            "WHEN MATCHED THEN UPDATE SET " +
            "nimi_fi=:nimi_fi, nimi_sv=:nimi_sv, nimi_en=:nimi_en, aloituspaikat=:aloituspaikat, " +
            " hakukelp_fi=:hakukelp_fi, hakukelp_sv=:hakukelp_sv, hakukelp_en=:hakukelp_en, " +
            "lisatiedot_fi=:lisatiedot_fi, lisatiedot_sv=:lisatiedot_sv, lisatiedot_en=:lisatiedot_en, " +
            "valintaper_fi=:valintaper_fi, valintaper_sv=:valintaper_sv, valintaper_en=:valintaper_en, " +
            "sorakuvaus_fi=:sorakuvaus_fi, sorakuvaus_sv=:sorakuvaus_sv, sorakuvaus_en=:sorakuvaus_en " +
            "WHEN NOT MATCHED THEN INSERT " +
            "(id, nimi_fi, nimi_sv, nimi_en, aloituspaikat, hakukelp_fi, hakukelp_sv, hakukelp_en, " +
            "lisatiedot_fi, lisatiedot_sv, lisatiedot_en, valintaper_fi, valintaper_sv, valintaper_en, " +
            "sorakuvaus_fi, sorakuvaus_sv, sorakuvaus_en) " +
            "VALUES (:id, :nimi_fi, :nimi_sv, :nimi_en, :aloituspaikat, :hakukelp_fi, :hakukelp_sv, :hakukelp_en, " +
            ":lisatiedot_fi, :lisatiedot_sv, :lisatiedot_en, :valintaper_fi, :valintaper_sv, :valintaper_en, " +
            ":sorakuvaus_fi, :sorakuvaus_sv, :sorakuvaus_en)")
    void upsert(@BindApplicationOption ApplicationOption applicationOption);

    @SqlQuery("select  " +
            "h.*,  " +
            "v.id as v_id, v.kieli as v_kieli, v.tyyppi as v_tyyppi,  v.kuvaus as v_kuvaus, " +
            "ak.id as ak_id, ak.alkaa as ak_alkaa, ak.loppuu as ak_loppuu, " +
            "ak.kuvaus as ak_kuvaus, ak.osoite as ak_osoite, ak.postinumero as ak_postinumero, " +
            "ak.ptoimipaikka as ak_ptoimipaikka, " +
            "l.id as l_id, l.kieli as l_kieli, l.nimi as l_nimi, l.erapaiva as l_erapaiva, " +
            "l.kuvaus as l_kuvaus, l.osoite as l_osoite, l.postinumero as l_postinumero, " +
            "l.ptoimipaikka as l_ptoimipaikka " +
            "from hakukohde h " +
            "inner join " +
            "valintakoe v on v.id_hakukohde = h.id " +
            "inner join " +
            "valintakoe_ak ak on ak.id_valintakoe = v.id " +
            "inner join " +
            "liite l on l.id_hakukohde = h.id " +
            "where h.id = :id")
    @Mapper(ApplicationOptionJoinRowMapper.class)
    List<ApplicationOptionJoinRow> findJoinRowsById(@Bind("id") String id);

    @SqlQuery("select  " +
            "h.*,  " +
            "v.id as v_id, v.kieli as v_kieli, v.tyyppi as v_tyyppi,  v.kuvaus as v_kuvaus, " +
            "ak.id as ak_id, ak.alkaa as ak_alkaa, ak.loppuu as ak_loppuu, " +
            "ak.kuvaus as ak_kuvaus, ak.osoite as ak_osoite, ak.postinumero as ak_postinumero, " +
            "ak.ptoimipaikka as ak_ptoimipaikka, " +
            "l.id as l_id, l.kieli as l_kieli, l.nimi as l_nimi, l.erapaiva as l_erapaiva, " +
            "l.kuvaus as l_kuvaus, l.osoite as l_osoite, l.postinumero as l_postinumero, " +
            "l.ptoimipaikka as l_ptoimipaikka " +
            "from hakukohde h " +
            "inner join " +
            "valintakoe v on v.id_hakukohde = h.id " +
            "inner join " +
            "valintakoe_ak ak on ak.id_valintakoe = v.id " +
            "inner join " +
            "liite l on l.id_hakukohde = h.id")
    @Mapper(ApplicationOptionJoinRowMapper.class)
    List<ApplicationOptionJoinRow> findJoinRows();

    @SqlBatch("MERGE INTO valintakoe USING dual on ( id = :id ) " +
            "WHEN MATCHED THEN UPDATE SET " +
            "kieli=:kieli, tyyppi=:tyyppi, kuvaus=:kuvaus " +
            "WHEN NOT MATCHED THEN INSERT " +
            "(id, kieli, tyyppi, kuvaus, id_hakukohde) VALUES (:id, :kieli, :tyyppi, :kuvaus, :id_hakukohde)")
    @BatchChunkSize(10)
    void upsertExams(@BindExam List<Exam> exam, @Bind("id_hakukohde") String applicationOptionOid);

    @SqlBatch("MERGE INTO valintakoe_ak USING dual on ( id = :id ) " +
            "WHEN MATCHED THEN UPDATE SET " +
            "alkaa=:alkaa, loppuu=:loppuu, kuvaus=:kuvaus, " +
            "osoite=:osoite, postinumero=:postinumero, ptoimipaikka=:ptoimipaikka " +
            "WHEN NOT MATCHED THEN INSERT " +
            "(id, alkaa, loppuu, kuvaus, osoite, postinumero, ptoimipaikka, id_valintakoe) " +
            "VALUES (:id, :alkaa, :loppuu, :kuvaus, :osoite, :postinumero, :ptoimipaikka, :id_valintakoe)")
    @BatchChunkSize(10)
    void upsertExamEvents(@BindExamEvent List<ExamEvent> events, @Bind("id_valintakoe") String examOid);


    @SqlUpdate("DELETE FROM liite " +
            "WHERE id_hakukohde=:id_hakukohde")
    void removeAttachments(@Bind("id_hakukohde") String applicationOptionOid);

    @SqlBatch("INSERT INTO liite " +
            "(id, kieli, nimi, kuvaus, erapaiva, osoite, postinumero, ptoimipaikka, id_hakukohde) " +
            "VALUES (:id, :kieli, :nimi, :kuvaus, :erapaiva, :osoite, :postinumero, :ptoimipaikka, :id_hakukohde)")
    @BatchChunkSize(10)
    void insertAttachments(@BindAttachment List<Attachment> attachments, @Bind("id_hakukohde") String applicationOptionOid);
}
