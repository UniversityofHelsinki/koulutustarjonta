package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.jdbi.ApplicationOptionJDBI;
import fi.helsinki.koulutustarjonta.dao.mapper.ApplicationOptionObjectGraphBuilder;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionDAO {

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
            jdbi.insertApplicationSuitabilityRequirements(applicationOption.getRequirements(),
                    applicationOption.getOid());
        }
    }

    public ApplicationOption findByOid(String oid) {
        ApplicationOption applicationOption = ApplicationOptionObjectGraphBuilder.build(jdbi.findJoinRowsById(oid)).get(0);
        return applicationOption;
    }

    public List<ApplicationOption> findAll() {
        return ApplicationOptionObjectGraphBuilder.build(jdbi.findJoinRows());
    }
}
