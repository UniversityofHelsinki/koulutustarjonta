package fi.helsinki.koulutustarjonta.resource;

import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.dto.I18NDTO;
import fi.helsinki.koulutustarjonta.dto.LearningOpportunityDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
@Path("/koulutus")
@Produces(MediaType.APPLICATION_JSON)
public class LearningOpportunityResource {

    LearningOpportunityDAO learningOpportunityDAO;

    public LearningOpportunityResource(LearningOpportunityDAO learningOpportunityDAO) {
        this.learningOpportunityDAO = learningOpportunityDAO;
    }

    @GET
    public List<LearningOpportunityDTO> getLearningOpportunities() {
        List<LearningOpportunity> learningOpportunities = learningOpportunityDAO.findAll();
        return learningOpportunities.stream().map(lo -> {
            return new LearningOpportunityDTO(lo.getOid(),
                    new I18NDTO(lo.getGoals().getFi(), null, null));
        }).collect(Collectors.toList());

    }

}
