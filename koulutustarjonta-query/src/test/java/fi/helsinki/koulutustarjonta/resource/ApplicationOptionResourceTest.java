package fi.helsinki.koulutustarjonta.resource;

import fi.helsinki.koulutustarjonta.dao.ApplicationOptionDAO;
import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.mapping.ApplicationOptionModelMapper;
import fi.helsinki.koulutustarjonta.test.Fixture;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.fest.util.Lists;
import org.junit.Before;
import org.junit.ClassRule;
import org.modelmapper.ModelMapper;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionResourceTest {


    private final String aoOid = "ao oid";

    private static final ApplicationOptionDAO dao = mock(ApplicationOptionDAO.class);
    private final String oid = "1.2.3";
    private final ApplicationOption applicationOption = Fixture.applicationOption(aoOid);
    private final ModelMapper modelMapper = new ApplicationOptionModelMapper();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ApplicationOptionResource(dao))
            .build();

    @Before
    public void setup() throws ResourceNotFound {
        when(dao.findByOid(eq(oid))).thenReturn(applicationOption);
        when(dao.findAll()).thenReturn(Lists.newArrayList(applicationOption));
    }
}
