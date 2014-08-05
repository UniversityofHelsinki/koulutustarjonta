package fi.helsinki.koulutustarjonta.resource;

import fi.helsinki.koulutustarjonta.dao.ApplicationOptionDAO;
import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dto.ApplicationOptionDTO;
import fi.helsinki.koulutustarjonta.mapping.ApplicationOptionModelMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
@Path("/hakukohde")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationOptionResource {

    private final ApplicationOptionDAO applicationOptionDAO;
    private final ApplicationOptionModelMapper modelMapper;

    public ApplicationOptionResource(ApplicationOptionDAO applicationOptionDAO) {
        this.applicationOptionDAO = applicationOptionDAO;
        this.modelMapper = new ApplicationOptionModelMapper();
    }

    @GET
    public List<ApplicationOptionDTO> getApplicationOptions() {
        return applicationOptionDAO.findAll().parallelStream()
                .map(ao -> modelMapper.map(ao, ApplicationOptionDTO.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{oid}")
    public ApplicationOptionDTO getApplicationOption(@PathParam("oid") String oid) throws ResourceNotFound{
        return modelMapper.map(applicationOptionDAO.findByOid(oid), ApplicationOptionDTO.class);
    }

}
