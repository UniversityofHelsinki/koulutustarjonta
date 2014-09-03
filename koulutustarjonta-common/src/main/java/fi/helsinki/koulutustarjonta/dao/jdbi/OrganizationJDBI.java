package fi.helsinki.koulutustarjonta.dao.jdbi;

import fi.helsinki.koulutustarjonta.dao.binder.BindContactInfo;
import fi.helsinki.koulutustarjonta.dao.binder.BindOrganization;
import fi.helsinki.koulutustarjonta.domain.ContactInfo;
import fi.helsinki.koulutustarjonta.domain.Organization;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@UseStringTemplate3StatementLocator
public interface OrganizationJDBI extends Transactional<OrganizationJDBI> {

    // Broken in oracle DB 11.2.0.2.0, should work in 11.2.0.4.0
    @Deprecated
    @SqlUpdate("MERGE INTO organisaatio USING dual ON ( id=:id ) " +
            "WHEN MATCHED THEN UPDATE SET " +
            "nimi_fi=:nimi_fi, nimi_sv=:nimi_sv, nimi_en=:nimi_en," +
            "kustannukset_fi=:kustannukset_fi, kustannukset_sv=:kustannukset_sv, kustannukset_en=:kustannukset_en," +
            "kv_koulohj_fi=:kv_koulohj_fi, kv_koulohj_sv=:kv_koulohj_sv, kv_koulohj_en=:kv_koulohj_en, " +
            "opliikkuvuus_fi=:opliikkuvuus_fi, opliikkuvuus_sv=:opliikkuvuus_sv, opliikkuvuus_en=:opliikkuvuus_en, " +
            "oppimisymparisto_fi=:oppimisymparisto_fi, oppimisymparisto_sv=:oppimisymparisto_sv, oppimisymparisto_en=:oppimisymparisto_en, " +
            "yleiskuvaus_fi=:yleiskuvaus_fi, yleiskuvaus_sv=:yleiskuvaus_sv, yleiskuvaus_en=:yleiskuvaus_en, " +
            "facebook_fi=:facebook_fi, facebook_sv=:facebook_sv, facebook_en=:facebook_en, " +
            "twitter_fi=:twitter_fi, twitter_sv=:twitter_sv, twitter_en=:twitter_en, " +
            "google_plus_fi=:google_plus_fi, google_plus_sv=:google_plus_sv, google_plus_en=:google_plus_en, " +
            "linkedin_fi=:linkedin_fi, linkedin_sv=:linkedin_sv, linkedin_en=:linkedin_en "  +
            "WHEN NOT MATCHED THEN INSERT " +
            "(id, nimi_fi, nimi_sv, nimi_en, kustannukset_fi, kustannukset_sv, kustannukset_en, " +
            "kv_koulohj_fi, kv_koulohj_sv, kv_koulohj_en, opliikkuvuus_fi, opliikkuvuus_sv, opliikkuvuus_en," +
            "oppimisymparisto_fi, oppimisymparisto_sv, oppimisymparisto_en, " +
            "yleiskuvaus_fi, yleiskuvaus_sv, yleiskuvaus_en, facebook_fi, facebook_sv, facebook_en, " +
            "twitter_fi, twitter_sv, twitter_en, google_plus_fi, google_plus_sv, google_plus_en, " +
            "linekdin_fi, linkedin_sv, linkedin_en) " +
            "VALUES (:id, :nimi_fi, :nimi_sv, :nimi_en, :kustannukset_fi, :kustannukset_sv, :kustannukset_en, " +
            ":kv_koulohj_fi, :kv_koulohj_sv, :kv_koulohj_en, :opliikkuvuus_fi, :opliikkuvuus_sv, :opliikkuvuus_en," +
            ":oppimisymparisto_fi, :oppimisymparisto_sv, :oppimisymparisto_en, " +
            ":yleiskuvaus_fi, :yleiskuvaus_sv, :yleiskuvaus_en, :facebook_fi, :facebook_sv, :facebook_en, " +
            ":twitter_fi, :twitter_sv, :twitter_en, :google_plus_fi, :google_plus_sv, :google_plus_en, " +
            ":linekdin_fi, :linkedin_sv, :linkedin_en)")
    void upsert(@BindOrganization Organization organization);

    @SqlUpdate("INSERT INTO organisaatio " +
            "(id, nimi_fi, nimi_sv, nimi_en, kustannukset_fi, kustannukset_sv, kustannukset_en, " +
            "kv_koulohj_fi, kv_koulohj_sv, kv_koulohj_en, opliikkuvuus_fi, opliikkuvuus_sv, opliikkuvuus_en," +
            "oppimisymparisto_fi, oppimisymparisto_sv, oppimisymparisto_en, " +
            "yleiskuvaus_fi, yleiskuvaus_sv, yleiskuvaus_en, facebook_fi, facebook_sv, facebook_en, " +
            "twitter_fi, twitter_sv, twitter_en, google_plus_fi, google_plus_sv, google_plus_en, " +
            "linkedin_fi, linkedin_sv, linkedin_en) " +
            "VALUES (:id, :nimi_fi, :nimi_sv, :nimi_en, :kustannukset_fi, :kustannukset_sv, :kustannukset_en, " +
            ":kv_koulohj_fi, :kv_koulohj_sv, :kv_koulohj_en, :opliikkuvuus_fi, :opliikkuvuus_sv, :opliikkuvuus_en," +
            ":oppimisymparisto_fi, :oppimisymparisto_sv, :oppimisymparisto_en, " +
            ":yleiskuvaus_fi, :yleiskuvaus_sv, :yleiskuvaus_en, :facebook_fi, :facebook_sv, :facebook_en, " +
            ":twitter_fi, :twitter_sv, :twitter_en, :google_plus_fi, :google_plus_sv, :google_plus_en, " +
            ":linkedin_fi, :linkedin_sv, :linkedin_en)")
    void insert(@BindOrganization Organization organization);

    @SqlUpdate("UPDATE organisaatio " +
            "SET nimi_fi=:nimi_fi, nimi_sv=:nimi_sv, nimi_en=:nimi_en," +
            "kustannukset_fi=:kustannukset_fi, kustannukset_sv=:kustannukset_sv, kustannukset_en=:kustannukset_en," +
            "kv_koulohj_fi=:kv_koulohj_fi, kv_koulohj_sv=:kv_koulohj_sv, kv_koulohj_en=:kv_koulohj_en, " +
            "opliikkuvuus_fi=:opliikkuvuus_fi, opliikkuvuus_sv=:opliikkuvuus_sv, opliikkuvuus_en=:opliikkuvuus_en, " +
            "oppimisymparisto_fi=:oppimisymparisto_fi, oppimisymparisto_sv=:oppimisymparisto_sv, oppimisymparisto_en=:oppimisymparisto_en, " +
            "yleiskuvaus_fi=:yleiskuvaus_fi, yleiskuvaus_sv=:yleiskuvaus_sv, yleiskuvaus_en=:yleiskuvaus_en, " +
            "facebook_fi=:facebook_fi, facebook_sv=:facebook_sv, facebook_en=:facebook_en, " +
            "twitter_fi=:twitter_fi, twitter_sv=:twitter_sv, twitter_en=:twitter_en, " +
            "google_plus_fi=:google_plus_fi, google_plus_sv=:google_plus_sv, google_plus_en=:google_plus_en, " +
            "linkedin_fi=:linkedin_fi, linkedin_sv=:linkedin_sv, linkedin_en=:linkedin_en "  +
            "WHERE id=:id")
    int update(@BindOrganization Organization organization);

    @SqlBatch("MERGE INTO yhteystieto USING dual ON ( id=:id ) " +
            "WHEN MATCHED THEN UPDATE SET " +
            "tyyppi=:tyyppi, kieli:=kieli, www:=www, puhelin=:puhelin, email=:email, fax=:fax, " +
            "kaynti_osoite=:kaynti_osoite, kaynti_postinumero=:kaynti_postinumero, kaynti_postitoimipaikka=:kaynti_postitoimipaikka, " +
            "posti_osoite=:posti_osoite, posti_postinumero=:posti_postinumero, posti_postitoimipaikka=:posti_postitoimipaikka " +
            "WHEN NOT MATCHED THEN INSERT " +
            "(id, tyyppi, kieli, www, puhelin, email, fax, kaynti_osoite, kaynti_postinumero, kaynti_postitoimipaikka, " +
            "posti_osoite, posti_postinumero, posti_postitoimipaikka) " +
            "VALUES " +
            "(:id, :tyyppi, :kieli, :www, :puhelin, :email, :fax, :kaynti_osoite, :kaynti_postinumero, :kaynti_postitoimipaikka, " +
            ":posti_osoite, :posti_postinumero, :posti_postitoimipaikka)")
    @BatchChunkSize(10)
    void upsertContactInfos(@BindContactInfo List<ContactInfo> contactInfos);

    @SqlUpdate("DELETE FROM yhteystieto " +
            "WHERE id_organisaatio = :id_organisaatio " +
            "AND id NOT IN (<current>)")
    void removeDeletedContactInfos(@Bind("id_organisaatio") String organizationOid,
                                         @BindIn("current") List<String> currentContactInfoOids);

}
