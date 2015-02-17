package fi.helsinki.koulutustarjonta.resource;

import com.sun.jersey.api.NotFoundException;
import fi.helsinki.koulutustarjonta.dao.UpdateResultDAO;
import fi.helsinki.koulutustarjonta.domain.UpdateResult;
import fi.helsinki.koulutustarjonta.dto.UpdateResultDTO;
import org.modelmapper.ModelMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Sebastian Monte
 */
@Path("/paivitys")
@Produces(MediaType.APPLICATION_JSON)
public class UpdateResultResource {

    private final UpdateResultDAO updateResultDAO;
    private final ModelMapper modelMapper = new ModelMapper();

    public UpdateResultResource(UpdateResultDAO updateResultDAO) {
        this.updateResultDAO = updateResultDAO;
    }

    @GET
    public List<UpdateResultDTO> getUpdateResults() {
        return updateResultDAO.findLatest(10).parallelStream()
                .map(ur -> modelMapper.map(ur, UpdateResultDTO.class))
                .collect(toList());
    }

    @GET
    @Path(value = "/tila")
    public String lastUpdateStatus() {
        UpdateResult last = updateResultDAO.findLast();
        if (last == null) {
            throw new NotFoundException();
        }
        return last.getState().toString();
    }
}
