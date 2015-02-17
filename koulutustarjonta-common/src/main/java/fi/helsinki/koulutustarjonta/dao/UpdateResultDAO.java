package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.jdbi.UpdateResultJDBI;
import fi.helsinki.koulutustarjonta.domain.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Sebastian Monte
 */
public class UpdateResultDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UpdateResultDAO.class);

    private final UpdateResultJDBI jdbi;

    public UpdateResultDAO(UpdateResultJDBI jdbi) {
        this.jdbi = jdbi;
    }

    public void save(UpdateResult updateResult) {
        jdbi.begin();
        try {
            jdbi.insert(updateResult);
            jdbi.commit();
        } catch (Exception e) {
            LOG.warn("Failed to save update result, rolling back");
            jdbi.rollback();
            throw e;
        }
    }

    public List<UpdateResult> findLatest(int limit) {
        return jdbi.findLatest(limit);
    }

    public UpdateResult findLast() {
        List<UpdateResult> lastInserted = jdbi.findLatest(1);
        if (lastInserted.isEmpty()) {
            return null;
        }
        return lastInserted.get(0);
    }
}
