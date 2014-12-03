package fi.helsinki.koulutustarjonta.dao.jdbi;

import fi.helsinki.koulutustarjonta.dao.binder.BindApplicationPeriod;
import fi.helsinki.koulutustarjonta.dao.binder.BindApplicationSystem;
import fi.helsinki.koulutustarjonta.dao.mapper.ApplicationSystemJoinRowMapper;
import fi.helsinki.koulutustarjonta.dao.util.ApplicationSystemJoinRow;
import fi.helsinki.koulutustarjonta.domain.ApplicationPeriod;
import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@UseStringTemplate3StatementLocator
public interface ApplicationSystemJDBI extends Transactional<ApplicationSystemJDBI> {

    @SqlQuery("SELECT h.*, ha.id AS ha_id, " +
            "ha.nimi_fi AS ha_nimi_fi, ha.nimi_sv AS ha_nimi_sv, ha.nimi_en AS ha_nimi_en, " +
            "ha.alkaa AS ha_alkaa, ha.loppuu AS ha_loppuu " +
            "FROM haku h " +
            "LEFT JOIN " +
            "hakuaika ha ON ha.id_haku = h.id " +
            "WHERE h.id = :id")
    @Mapper(ApplicationSystemJoinRowMapper.class)
    List<ApplicationSystemJoinRow> findByOid(@Bind("id") String oid);

    @SqlQuery("SELECT h.*, ha.id AS ha_id, " +
            "ha.nimi_fi AS ha_nimi_fi, ha.nimi_sv AS ha_nimi_sv, ha.nimi_en AS ha_nimi_en, " +
            "ha.alkaa AS ha_alkaa, ha.loppuu AS ha_loppuu " +
            "FROM haku h " +
            "LEFT JOIN " +
            "hakuaika ha ON ha.id_haku = h.id")
    @Mapper(ApplicationSystemJoinRowMapper.class)
    List<ApplicationSystemJoinRow> findAll();

    @SqlUpdate("MERGE INTO haku USING dual on ( id = :id ) " +
            "WHEN MATCHED THEN UPDATE SET " +
            "nimi_fi=:nimi_fi, nimi_sv=:nimi_sv, nimi_en=:nimi_en, " +
            "hakutapa_fi=:hakutapa_fi, hakutapa_sv=:hakutapa_sv, hakutapa_en=:hakutapa_en, " +
            "hakukausi_vuosi=:hakukausi_vuosi, hakukausi_arvo=:hakukausi_arvo, " +
            "hakukausi_fi=:hakukausi_fi, hakukausi_sv=:hakukausi_sv, hakukausi_en=:hakukausi_en, " +
            "koul_alk_vuosi=:koul_alk_vuosi, koul_alk_kausi_arvo=:koul_alk_kausi_arvo, " +
            "koul_alk_kausi_fi=:koul_alk_kausi_fi, koul_alk_kausi_sv=:koul_alk_kausi_sv, " +
            "koul_alk_kausi_en=:koul_alk_kausi_en, hakulomake_url = :hakulomake_url " +
            "WHEN NOT MATCHED THEN INSERT " +
            "(id, nimi_fi, nimi_sv, nimi_en, hakutapa_fi, hakutapa_sv, hakutapa_en, " +
            "hakukausi_vuosi, hakukausi_arvo, hakukausi_fi, hakukausi_sv, hakukausi_en, " +
            "koul_alk_vuosi, koul_alk_kausi_arvo, koul_alk_kausi_fi, koul_alk_kausi_sv, " +
            "koul_alk_kausi_en, hakulomake_url) " +
            "VALUES " +
            "(:id, :nimi_fi, :nimi_sv, :nimi_en, :hakutapa_fi, :hakutapa_sv, :hakutapa_en, " +
            ":hakukausi_vuosi, :hakukausi_arvo, :hakukausi_fi, :hakukausi_sv, :hakukausi_en, " +
            ":koul_alk_vuosi, :koul_alk_kausi_arvo, :koul_alk_kausi_fi, :koul_alk_kausi_sv, " +
            ":koul_alk_kausi_en, :hakulomake_url)")
    void upsertApplicationSystem(@BindApplicationSystem ApplicationSystem applicationSystem);

    @SqlBatch("MERGE INTO hakuaika USING dual on ( id = :id ) " +
            "WHEN MATCHED THEN UPDATE SET " +
            "nimi_fi=:nimi_fi, nimi_sv=:nimi_sv, nimi_en=:nimi_en, " +
            " alkaa=:alkaa, loppuu=:loppuu " +
            "WHEN NOT MATCHED THEN INSERT " +
            "(id, nimi_fi, nimi_sv, nimi_en, alkaa, loppuu, id_haku) " +
            "VALUES " +
            "(:id, :nimi_fi, :nimi_sv, :nimi_en, :alkaa, :loppuu, :id_haku)")
    @BatchChunkSize(10)
    void upsertApplicationPeriods(@BindApplicationPeriod List<ApplicationPeriod> applicationPeriods,
                                  @Bind("id_haku") String applicationSystemOid);

    @SqlUpdate("DELETE FROM hakuaika " +
            "WHERE id_haku = :id_haku " +
            "AND id NOT IN (<current>)")
    void removeDeletedApplicationPeriods(@Bind("id_haku") String applicationSystemOid,
                                         @BindIn("current") List<String> currentPeriodIds);

    @SqlUpdate("UPDATE hakukohde SET id_hakuaika = NULL " +
            "WHERE id_haku=:id_haku AND id_hakuaika NOT IN (<current>)")
    void clearDeletedApplicationPeriodsFromApplicationOptions(@Bind("id_haku") String applicationSystemOid,
                                         @BindIn("current") List<String> currentPeriodIds);
}
