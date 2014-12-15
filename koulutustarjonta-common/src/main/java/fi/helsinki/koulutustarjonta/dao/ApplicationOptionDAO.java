package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dao.jdbi.ApplicationOptionJDBI;
import fi.helsinki.koulutustarjonta.dao.mapper.ApplicationOptionObjectMapper;
import fi.helsinki.koulutustarjonta.dao.util.ApplicationOptionJoinRow;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationOptionDAO.class);

    private final ApplicationOptionJDBI jdbi;

    public ApplicationOptionDAO(ApplicationOptionJDBI jdbi) {
        this.jdbi = jdbi;
    }

    public void save(ApplicationOption applicationOption) {
        LOG.debug(String.format("Saving application option %s", applicationOption.getOid()));
        jdbi.begin();
        try {
            jdbi.upsert(applicationOption);
            if (applicationOption.getExams() != null) {
                jdbi.upsertExams(applicationOption.getExams(), applicationOption.getOid());
                applicationOption.getExams().forEach(exam -> jdbi.upsertExamEvents(exam.getEvents(), exam.getOid()));
            }
            if (applicationOption.getAttachments() != null) {
                jdbi.removeAttachments(applicationOption.getOid());
                jdbi.insertAttachments(applicationOption.getAttachments(), applicationOption.getOid());
            }
            if (applicationOption.getRequirements() != null) {
                jdbi.removeRequirements(applicationOption.getOid());
                jdbi.insertRequirements(applicationOption.getRequirements(),
                        applicationOption.getOid());
            }
            jdbi.commit();
        } catch (Exception e) {
            LOG.warn("Failed to save application option, rolling back");
            jdbi.rollback();
            throw e;
        }
    }

    public ApplicationOption findByOid(String oid) throws ResourceNotFound {
        List<ApplicationOptionJoinRow> rows = jdbi.findJoinRowsById(oid);
        if (rows.size() == 0) {
            throw new ResourceNotFound(ApplicationOption.class, oid);
        }
        else {
           return ApplicationOptionObjectMapper.build(rows).get(0);
        }
    }

    public List<ApplicationOption> findAll() {
        return ApplicationOptionObjectMapper.build(jdbi.findJoinRows());
    }

}
