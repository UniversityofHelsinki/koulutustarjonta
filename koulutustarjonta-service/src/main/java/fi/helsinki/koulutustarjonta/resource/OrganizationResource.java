package fi.helsinki.koulutustarjonta.resource;

import fi.helsinki.koulutustarjonta.dao.OrganizationDAO;
import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dto.OrganizationDTO;
import fi.helsinki.koulutustarjonta.mapping.OrganizationModelMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
@Path("/organisaatio")
@Produces(MediaType.APPLICATION_JSON)
public class OrganizationResource {

    private final OrganizationDAO organizationDAO;
    private final OrganizationModelMapper modelMapper;

    public OrganizationResource(OrganizationDAO organizationDAO) {
        this.organizationDAO = organizationDAO;
        modelMapper = new OrganizationModelMapper();
    }

    @GET
    public List<OrganizationDTO> getOrganizations() {
        return organizationDAO.findAll().parallelStream()
                .map(o -> modelMapper.map(o, OrganizationDTO.class))
                .collect(toList());
    }

    @GET
    @Path("/{oid}")
    public OrganizationDTO getOrganization(@PathParam("oid") String oid) throws ResourceNotFound {
        return modelMapper.map(organizationDAO.findByOid(oid), OrganizationDTO.class);

    }

}
