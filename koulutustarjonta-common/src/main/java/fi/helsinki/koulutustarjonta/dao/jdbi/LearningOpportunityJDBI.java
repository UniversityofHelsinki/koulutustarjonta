package fi.helsinki.koulutustarjonta.dao.jdbi;

import fi.helsinki.koulutustarjonta.dao.binder.BindLearningOpportunity;
import fi.helsinki.koulutustarjonta.dao.binder.BindTeachingLanguage;
import fi.helsinki.koulutustarjonta.dao.mapper.LearningOpportunityJoinRowMapper;
import fi.helsinki.koulutustarjonta.dao.util.LearningOpportunityJoinRow;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@UseStringTemplate3StatementLocator
public interface LearningOpportunityJDBI {

    @SqlUpdate("MERGE INTO koulutus USING dual ON ( id=:id ) " +
            "WHEN MATCHED THEN UPDATE SET " +
            "tutkintonimike_fi = :tutkintonimike_fi, tutkintonimike_sv = :tutkintonimike_sv, tutkintonimike_en = :tutkintonimike_en, " +
            "opintoala_fi = :opintoala_fi, opintoala_sv = :opintoala_sv, opintoala_en = :opintoala_en, " +
            "tutkintoohjelma_fi = :tutkintoohjelma_fi, tutkintoohjelma_sv = :tutkintoohjelma_sv, tutkintoohjelma_en = :tutkintoohjelma_en, " +
            "alkamisvuosi = :alkamisvuosi, alkamiskausi_fi = :alkamiskausi_fi, alkamiskausi_sv = :alkamiskausi_sv, alkamiskausi_en = :alkamiskausi_en, " +
            "suunni_kesto = :suunni_kesto, suunni_tyyppi_fi = :suunni_tyyppi_fi, suunni_tyyppi_sv = :suunni_tyyppi_sv, suunni_tyyppi_en = :suunni_tyyppi_en, " +
            "laajuus = :laajuus, laajuus_tyyppi_fi = :laajuus_tyyppi_fi, laajuus_tyyppi_sv = :laajuus_tyyppi_sv, laajuus_tyyppi_en = :laajuus_tyyppi_en, " +
            "rakenne_fi = :rakenne_fi,  rakenne_sv = :rakenne_sv, rakenne_en = :rakenne_en," +
            "tavoitteet_fi = :tavoitteet_fi, tavoitteet_sv = :tavoitteet_sv, tavoitteet_en = :tavoitteet_en, " +
            "mahdollisuudet_fi = :mahdollisuudet_fi, mahdollisuudet_sv = :mahdollisuudet_sv, mahdollisuudet_en = :mahdollisuudet_en, " +
            "patevyys_fi = :patevyys_fi, patevyys_sv = :patevyys_sv, patevyys_en = :patevyys_en, " +
            "lisat_opkiel_fi = :lisat_opkiel_fi, lisat_opkiel_sv = :lisat_opkiel_sv, lisat_opkiel_en = :lisat_opkiel_en, " +
            "yhteistyo_fi = :yhteistyo_fi, yhteistyo_sv = :yhteistyo_sv, yhteistyo_en = :yhteistyo_en, " +
            "paaaineval_fi = :paaaineval_fi, paaaineval_sv = :paaaineval_sv, paaaineval_en = :paaaineval_en, " +
            "kansval_fi = :kansval_fi, kansval_sv = :kansval_sv, kansval_en = :kansval_en, " +
            "sijtyo_fi = :sijtyo_fi, sijtyo_sv = :sijtyo_sv, sijtyo_en = :sijtyo_en, " +
            "sisalto_fi = :sisalto_fi, sisalto_sv = :sisalto_sv, sisalto_en = :sisalto_en, " +
            "tutkpaino_fi = :tutkpaino_fi, tutkpaino_sv = :tutkpaino_sv, tutkpaino_en = :tutkpaino_en, " +
            "opinnaytetyo_fi = :opinnaytetyo_fi, opinnaytetyo_sv = :opinnaytetyo_sv, opinnaytetyo_en = :opinnaytetyo_en," +
            "id_organisaatio = :id_organisaatio " +
            "WHEN NOT MATCHED THEN INSERT " +
            "(id,  tutkintonimike_fi, tutkintonimike_sv, tutkintonimike_en, " +
            "opintoala_fi, opintoala_sv, opintoala_en, " +
            "tutkintoohjelma_fi, tutkintoohjelma_sv, tutkintoohjelma_en, " +
            "alkamisvuosi, alkamiskausi_fi, alkamiskausi_sv, alkamiskausi_en, " +
            "suunni_kesto, suunni_tyyppi_fi, suunni_tyyppi_sv, suunni_tyyppi_en, " +
            "laajuus, laajuus_tyyppi_fi, laajuus_tyyppi_sv, laajuus_tyyppi_en, " +
            "rakenne_fi, rakenne_sv, rakenne_en, " +
            "tavoitteet_fi, tavoitteet_sv, tavoitteet_en, " +
            "mahdollisuudet_fi, mahdollisuudet_sv, mahdollisuudet_en, " +
            "patevyys_fi, patevyys_sv, patevyys_en, " +
            "lisat_opkiel_fi, lisat_opkiel_sv, lisat_opkiel_en, " +
            "yhteistyo_fi, yhteistyo_sv, yhteistyo_en, " +
            "paaaineval_fi, paaaineval_sv, paaaineval_en, " +
            "kansval_fi, kansval_sv, kansval_en, " +
            "sijtyo_fi, sijtyo_sv, sijtyo_en, " +
            "sisalto_fi, sisalto_sv, sisalto_en, " +
            "tutkpaino_fi, tutkpaino_sv, tutkpaino_en, " +
            "opinnaytetyo_fi, opinnaytetyo_sv, opinnaytetyo_en, id_organisaatio)" +
            "values (:id,  :tutkintonimike_fi, :tutkintonimike_sv, :tutkintonimike_en, " +
            ":opintoala_fi, :opintoala_sv, :opintoala_en, " +
            ":tutkintoohjelma_fi, :tutkintoohjelma_sv, :tutkintoohjelma_en, " +
            ":alkamisvuosi, :alkamiskausi_fi, :alkamiskausi_sv, :alkamiskausi_en, " +
            ":suunni_kesto, :suunni_tyyppi_fi, :suunni_tyyppi_sv, :suunni_tyyppi_en, " +
            ":laajuus, :laajuus_tyyppi_fi, :laajuus_tyyppi_sv, :laajuus_tyyppi_en, " +
            ":rakenne_fi, :rakenne_sv, :rakenne_en, " +
            ":tavoitteet_fi, :tavoitteet_sv, :tavoitteet_en, " +
            ":mahdollisuudet_fi, :mahdollisuudet_sv, :mahdollisuudet_en, " +
            ":patevyys_fi, :patevyys_sv, :patevyys_en, " +
            ":lisat_opkiel_fi, :lisat_opkiel_sv, :lisat_opkiel_en, " +
            ":yhteistyo_fi, :yhteistyo_sv, :yhteistyo_en, " +
            ":paaaineval_fi, :paaaineval_sv, :paaaineval_en, " +
            ":kansval_fi, :kansval_sv, :kansval_en, " +
            ":sijtyo_fi, :sijtyo_sv, :sijtyo_en, " +
            ":sisalto_fi, :sisalto_sv, :sisalto_en, " +
            ":tutkpaino_fi, :tutkpaino_sv, :tutkpaino_en, " +
            ":opinnaytetyo_fi, :opinnaytetyo_sv, :opinnaytetyo_en, :id_organisaatio)")
    void upsert(@BindLearningOpportunity LearningOpportunity learningOpportunity);

    @SqlQuery("SELECT k.*, ok.kieli as opetuskieli_kieli, " +
            "ok.selite_fi AS opetuskieli_selite_fi, ok.selite_sv AS opetuskieli_selite_sv, " +
            "ok.selite_en AS opetuskieli_selite_en, hk.id_hakukohde AS hakukohde_id, " +
            "l.id_lapsi AS id_lapsi, v.id_vanhempi AS id_vanhempi " +
            "FROM koulutus k " +
            "LEFT JOIN " +
            "koulutus_opetuskieli ko ON k.id = ko.id_koulutus " +
            "LEFT JOIN " +
            "opetuskieli ok ON ok.id = ko.id_opetuskieli " +
            "LEFT JOIN " +
            "hakukohde_koulutus hk ON k.id = hk.id_koulutus " +
            "LEFT JOIN " +
            "koulutus_sisaltyvyys l ON k.id = l.id_vanhempi " +
            "LEFT JOIN " +
            "koulutus_sisaltyvyys v ON k.id = v.id_lapsi")
    @Mapper(LearningOpportunityJoinRowMapper.class)
    List<LearningOpportunityJoinRow> findAllJoinRows();

    @SqlQuery("SELECT k.*, ok.kieli as opetuskieli_kieli, " +
            "ok.selite_fi as opetuskieli_selite_fi, ok.selite_sv as opetuskieli_selite_sv, " +
            "ok.selite_en as opetuskieli_selite_en, hk.id_hakukohde AS hakukohde_id, " +
            "l.id_lapsi AS id_lapsi, v.id_vanhempi AS id_vanhempi " +
            "FROM koulutus k " +
            "LEFT JOIN " +
            "koulutus_opetuskieli ko on k.id = ko.id_koulutus " +
            "LEFT JOIN " +
            "opetuskieli ok on ok.id = ko.id_opetuskieli " +
            "LEFT JOIN " +
            "hakukohde_koulutus hk ON k.id = hk.id_koulutus " +
            "LEFT JOIN " +
            "koulutus_sisaltyvyys l ON k.id = l.id_vanhempi " +
            "LEFT JOIN " +
            "koulutus_sisaltyvyys v ON k.id = v.id_lapsi " +
            "WHERE k.id = :id")
    @Mapper(LearningOpportunityJoinRowMapper.class)
    List<LearningOpportunityJoinRow> findJoinRowsById(@Bind("id") String id);

    @SqlBatch("MERGE INTO opetuskieli USING dual on ( id = :id ) " +
               "WHEN NOT MATCHED THEN INSERT (id, kieli, selite_fi, selite_sv, selite_en) VALUES (:id, :kieli, :selite_fi, :selite_sv, :selite_en)")
    @BatchChunkSize(10)
    void insertTeachingLanguages(@BindTeachingLanguage List<TeachingLanguage> teachingLanguage);

    @SqlBatch("INSERT INTO koulutus_opetuskieli (id_koulutus, id_opetuskieli) VALUES (:id_koulutus, :id_opetuskieli)")
    @BatchChunkSize(10)
    void addTeachingLanguagesToLearningOpportunity(@Bind("id_koulutus") String learningOpportunityId,
                                                            @Bind("id_opetuskieli") List<String> teachingLanguageIds);

    @SqlUpdate("DELETE FROM koulutus_opetuskieli WHERE id_koulutus = :id")
    void removeTeachingLanguagesFromLearningOpportunity(@Bind("id") String learningOpportunityId);

    @SqlBatch("MERGE INTO hakukohde_koulutus USING dual ON (id_koulutus = :id_koulutus AND id_hakukohde = :id_hakukohde) " +
            "WHEN NOT MATCHED THEN INSERT (id_koulutus, id_hakukohde) VALUES (:id_koulutus, :id_hakukohde)")
    @BatchChunkSize(10)
    void addApplicationOptions(@Bind("id_koulutus") String learningOpportunityOid, @Bind("id_hakukohde") List<String> applicationOptionOids);

    @SqlUpdate("DELETE FROM hakukohde_koulutus " +
            "WHERE id_koulutus = :id_koulutus " +
            "AND id_hakukohde NOT IN (<current>)")
    void removeDeletedApplicationOptions(@Bind("id_koulutus") String learningOpportunityOid,
                                         @BindIn("current") List<String> currentApplicationOptionOids);

    @SqlUpdate("MERGE INTO koulutus_sisaltyvyys " +
            "USING dual " +
            "ON (id_lapsi = :id_lapsi AND id_vanhempi = :id_vanhempi) " +
            "WHEN NOT MATCHED THEN " +
            "INSERT (id_lapsi, id_vanhempi) VALUES (:id_lapsi, :id_vanhempi) " +
            "WHERE EXISTS ( " +
            "SELECT 1 " +
            "FROM koulutus k " +
            "WHERE k.id = :id_vanhempi)")
    void addParent(@Bind("id_lapsi") String childOid, @Bind("id_vanhempi") String parentOid);

    @SqlBatch("MERGE INTO koulutus_sisaltyvyys " +
            "USING dual " +
            "ON (id_lapsi = :id_lapsi AND id_vanhempi = :id_vanhempi) " +
            "WHEN NOT MATCHED THEN " +
            "INSERT (id_lapsi, id_vanhempi) VALUES (:id_lapsi, :id_vanhempi) " +
            "WHERE EXISTS ( " +
            "SELECT 1 " +
            "FROM koulutus k " +
            "WHERE k.id = :id_lapsi)")
    @BatchChunkSize(10)
    void addChildren(@Bind("id_vanhempi") String parentOid, @Bind("id_lapsi") List<String> childOids);
}
