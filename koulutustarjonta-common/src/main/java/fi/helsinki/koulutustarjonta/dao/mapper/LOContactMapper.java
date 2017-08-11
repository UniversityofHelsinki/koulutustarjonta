package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.LOContact;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static fi.helsinki.koulutustarjonta.dao.mapper.MapperUtil.resolveStringList;

public class LOContactMapper implements ResultSetMapper<LOContact> {
    private static final Logger log = Logger.getLogger(LOContactMapper.class);

    @Override
    public LOContact map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        long id = r.getLong("ID");
        String nimi = r.getString("NIMI");
        String email = r.getString("EMAIL");
        String puhelinnumero = r.getString("PUHELINNUMERO");
        String tyyppi = r.getString("TYYPPI");
        String title = r.getString("TITTELI");
        List<String> kieli = resolveStringList(r, "KIELI");

        return LOContact.builder()
                .id(id)
                .title(title)
                .phoneNumber(puhelinnumero)
                .name(nimi)
                .languages(kieli)
                .contactType(tyyppi)
                .email(email)
                .build();
    }
}