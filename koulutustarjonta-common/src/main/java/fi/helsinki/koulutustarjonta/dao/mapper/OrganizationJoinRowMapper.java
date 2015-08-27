package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.dao.util.OrganizationJoinRow;
import fi.helsinki.koulutustarjonta.domain.ContactInfo;
import fi.helsinki.koulutustarjonta.domain.Organization;
import fi.helsinki.koulutustarjonta.domain.Some;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hannu Lyytikainen
 */
public class OrganizationJoinRowMapper implements ResultSetMapper<OrganizationJoinRow> {

    private final ContactInfoMapper contactInfoMapper;

    public OrganizationJoinRowMapper() {
        this.contactInfoMapper = new ContactInfoMapper();
    }

    @Override
    public OrganizationJoinRow map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Organization o = new Organization(r.getString("id"), MapperUtil.resolveI18N(r, "nimi"),
                MapperUtil.resolveI18N(r, "yleiskuvaus"), MapperUtil.resolveI18N(r, "kustannukset"),
                MapperUtil.resolveI18N(r, "kv_koulohj"), MapperUtil.resolveI18N(r, "opliikkuvuus"),
                MapperUtil.resolveI18N(r, "oppimisymparisto"), MapperUtil.resolveI18N(r, "saavutettavuus"),
                MapperUtil.resolveI18N(r, "vuosikello"), MapperUtil.resolveI18N(r, "vastuuhenkilot"),
                MapperUtil.resolveI18N(r, "valintamenettely"), MapperUtil.resolveI18N(r, "aik_kokemus"),
                MapperUtil.resolveI18N(r, "kieliopinnot"), MapperUtil.resolveI18N(r, "tyoharjoittelu"),
                new Some(r.getString("id"),
                         MapperUtil.resolveI18N(r, "facebook"),
                         MapperUtil.resolveI18N(r, "google_plus"),
                         MapperUtil.resolveI18N(r, "linkedin"),
                         MapperUtil.resolveI18N(r, "twitter"),
                         MapperUtil.resolveI18N(r, "some_other"),
                         MapperUtil.resolveI18N(r, "instagram"),
                         MapperUtil.resolveI18N(r, "youtube")
                ),
                null,
                null
        );
        ContactInfo ci = contactInfoMapper.map(index, r, ctx);
        return new OrganizationJoinRow(o, ci);
    }
}
