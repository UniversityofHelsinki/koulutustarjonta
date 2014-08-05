package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dao.jdbi.ApplicationOptionJDBI;
import fi.helsinki.koulutustarjonta.dao.mapper.ApplicationOptionObjectGraphBuilder;
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
        int updated = jdbi.update(applicationOption);
        if (updated == 0) {
            jdbi.insert(applicationOption);
        }
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
    }

    public ApplicationOption findByOid(String oid) throws ResourceNotFound {
        List<ApplicationOptionJoinRow> rows = jdbi.findJoinRowsById(oid);
        if (rows.size() == 0) {
            throw new ResourceNotFound(ApplicationOption.class, oid);
        }
        else {
           return ApplicationOptionObjectGraphBuilder.build(rows).get(0);
        }
    }

    public List<ApplicationOption> findAll() {
        return ApplicationOptionObjectGraphBuilder.build(jdbi.findJoinRows());
    }
}