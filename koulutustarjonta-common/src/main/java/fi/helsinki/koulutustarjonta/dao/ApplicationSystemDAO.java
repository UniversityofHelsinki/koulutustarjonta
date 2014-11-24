package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dao.jdbi.ApplicationSystemJDBI;
import fi.helsinki.koulutustarjonta.dao.mapper.ApplicationSystemObjectBuilder;
import fi.helsinki.koulutustarjonta.dao.util.ApplicationSystemJoinRow;
import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationSystemDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationSystemDAO.class);

    private final ApplicationSystemJDBI jdbi;

    public ApplicationSystemDAO(ApplicationSystemJDBI jdbi) {
        this.jdbi = jdbi;
    }

    public void save(ApplicationSystem as) {
        LOG.debug(String.format("Saving application system %s", as.getOid()));
        jdbi.upsertApplicationSystem(as);
        jdbi.upsertApplicationPeriods(as.getApplicationPeriods(),
                as.getOid());
        jdbi.removeDeletedApplicationPeriods(as.getOid(),
                as.getApplicationPeriods().stream()
                        .map(period -> period.getId())
                        .collect(Collectors.toList())
        );
    }

    public ApplicationSystem findByOid(String oid) throws ResourceNotFound {
        List<ApplicationSystemJoinRow> rows = jdbi.findByOid(oid);
        if (rows.size() == 0) {
            throw new ResourceNotFound(ApplicationSystem.class, oid);
        }
        else {
            return ApplicationSystemObjectBuilder.buildOne(rows);
        }
    }

    public List<ApplicationSystem> findAll() {
        return ApplicationSystemObjectBuilder.build(jdbi.findAll());
    }
}
