package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.dao.util.ApplicationOptionJoinRow;
import fi.helsinki.koulutustarjonta.domain.*;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static fi.helsinki.koulutustarjonta.dao.mapper.MapperUtil.resolveI18N;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionJoinRowMapper implements ResultSetMapper<ApplicationOptionJoinRow> {

    private final ExamMapper examMapper;
    private final ExamEventMapper eventMapper;
    private final AttachmentMapper attachmentMapper;
    private final RequirementMapper requirementMapper;


    public ApplicationOptionJoinRowMapper() {
        this.examMapper = new ExamMapper();
        this.eventMapper = new ExamEventMapper();
        this.attachmentMapper = new AttachmentMapper();
        this.requirementMapper = new RequirementMapper();
    }

    @Override
    public ApplicationOptionJoinRow map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        ApplicationOption ao = new ApplicationOption();
        ao.setOid(r.getString("id"));
        ao.setName(resolveI18N(r, "nimi"));
        ao.setStartingQuota(r.getInt("aloituspaikat"));
        ao.setStartingQuotaDescription(resolveI18N(r, "aloituspaikat"));
        ao.setRequirementDescription(resolveI18N(r, "hakukelp_kuvaus"));
        ao.setAdditionalInfo(resolveI18N(r, "lisatiedot"));
        ao.setSelectionCriteria(resolveI18N(r, "valintaper"));
        ao.setSora(resolveI18N(r, "sorakuvaus"));
        ao.setApplicationSystem(r.getString("id_haku"));
        ao.setApplicationPeriod(r.getString("id_hakuaika"));
        Exam exam = examMapper.map(index, r, ctx);
        ExamEvent event = eventMapper.map(index, r, ctx);
        Attachment attachment = attachmentMapper.map(index, r, ctx);
        Requirement requirement = requirementMapper.map(index, r,ctx);
        return new ApplicationOptionJoinRow(ao, exam, event, attachment, requirement);
    }
}
