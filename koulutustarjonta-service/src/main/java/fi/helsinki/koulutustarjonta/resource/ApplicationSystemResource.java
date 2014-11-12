package fi.helsinki.koulutustarjonta.resource;

import fi.helsinki.koulutustarjonta.dao.ApplicationSystemDAO;
import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dto.ApplicationSystemDTO;
import fi.helsinki.koulutustarjonta.mapping.ApplicationSystemModelMapper;

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
@Path("/haku")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationSystemResource {

    private final ApplicationSystemDAO dao;
    private final ApplicationSystemModelMapper modelMapper;

    public ApplicationSystemResource(ApplicationSystemDAO dao) {
        this.dao = dao;
        this.modelMapper = new ApplicationSystemModelMapper();
    }

    @GET
    @Path("/{oid}")
    public ApplicationSystemDTO getApplicationSystem(@PathParam("oid") String oid) throws ResourceNotFound {
        return modelMapper.map(dao.findByOid(oid), ApplicationSystemDTO.class);
    }

    @GET
    public List<ApplicationSystemDTO> getApplicationSystems() {
        return dao.findAll().parallelStream()
                .map(as -> modelMapper.map(as, ApplicationSystemDTO.class))
                .collect(Collectors.toList());
    }
}
