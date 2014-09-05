package fi.helsinki.koulutustarjonta.resource;

import com.google.common.collect.Lists;
import com.sun.jersey.api.client.GenericType;
import fi.helsinki.koulutustarjonta.dao.OrganizationDAO;
import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.domain.Organization;
import fi.helsinki.koulutustarjonta.dto.ContactInfoDTO;
import fi.helsinki.koulutustarjonta.dto.OrganizationDTO;
import fi.helsinki.koulutustarjonta.dto.SomeDTO;
import fi.helsinki.koulutustarjonta.test.Fixture;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Hannu Lyytikainen
 */
public class OrganizationResourceTest {

    private final String organizationOid = "org_oid";

    private static final OrganizationDAO dao = mock(OrganizationDAO.class);
    private final Organization organization = Fixture.organization(organizationOid);
    private final ModelMapper modelMapper = new ModelMapper();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new OrganizationResource(dao))
            .build();

    @Before
    public void setup() throws ResourceNotFound {
        when(dao.findByOid(eq(organizationOid))).thenReturn(organization);
        when(dao.findAll()).thenReturn(Lists.newArrayList(organization));
    }

    @Test
    public void testGetOrganizations() {
        List<OrganizationDTO> response = resources.client().resource("/organisaatio")
                .get(new GenericType<List<OrganizationDTO>>() {});
        OrganizationDTO expected = modelMapper.map(organization, OrganizationDTO.class);
        organizationDTOsEqual(expected, response.get(0));
        verify(dao).findAll();

    }

    private void organizationDTOsEqual(OrganizationDTO expected, OrganizationDTO actual) {
        assertEquals(expected.getOid(), actual.getOid());
        DTOTestUtil.i18NDTOsEqual(expected.getName(), actual.getName());
        DTOTestUtil.i18NDTOsEqual(expected.getOutline(), actual.getOutline());
        DTOTestUtil.i18NDTOsEqual(expected.getExpenses(), actual.getExpenses());
        DTOTestUtil.i18NDTOsEqual(expected.getInternationalStudyPrograms(), actual.getInternationalStudyPrograms());
        DTOTestUtil.i18NDTOsEqual(expected.getStudentTransfer(), actual.getStudentTransfer());
        DTOTestUtil.i18NDTOsEqual(expected.getStudyEnvironment(), actual.getStudyEnvironment());
        someDTOsEqual(expected.getSome(), actual.getSome());
        contactInfoDTOsEqual(filterLang(expected.getContactInfos(), "fi"), filterLang(actual.getContactInfos(), "fi"));
        contactInfoDTOsEqual(filterLang(expected.getContactInfos(), "sv"), filterLang(actual.getContactInfos(), "sv"));
        contactInfoDTOsEqual(filterLang(expected.getContactInfos(), "en"), filterLang(actual.getContactInfos(), "en"));
        contactInfoDTOsEqual(filterLang(expected.getApplicantServices(), "fi"), filterLang(actual.getApplicantServices(), "fi"));
        contactInfoDTOsEqual(filterLang(expected.getApplicantServices(), "sv"), filterLang(actual.getApplicantServices(), "sv"));
        contactInfoDTOsEqual(filterLang(expected.getApplicantServices(), "en"), filterLang(actual.getApplicantServices(), "en"));
    }

    private void someDTOsEqual(SomeDTO expected, SomeDTO actual) {
        DTOTestUtil.i18NDTOsEqual(expected.getFacebook(), actual.getFacebook());
        DTOTestUtil.i18NDTOsEqual(expected.getTwitter(), actual.getTwitter());
        DTOTestUtil.i18NDTOsEqual(expected.getGooglePlus(), actual.getGooglePlus());
        DTOTestUtil.i18NDTOsEqual(expected.getLinkedIn(), actual.getLinkedIn());
    }

    private ContactInfoDTO filterLang(List<ContactInfoDTO> contactInfos, String lang) {
        return contactInfos.stream()
                .filter(info -> info.getLang().equals(lang))
                .collect(toList()).get(0);
    }

    private void contactInfoDTOsEqual(ContactInfoDTO expected, ContactInfoDTO actual) {
        assertEquals(expected.getOid(), actual.getOid());
        assertEquals(expected.getLang(), actual.getLang());
        assertEquals(expected.getWww(), actual.getWww());
        assertEquals(expected.getPhone(), actual.getPhone());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getFax(), actual.getFax());
        assertEquals(expected.getVisitingAddress().getStreet(), actual.getVisitingAddress().getStreet());
        assertEquals(expected.getVisitingAddress().getPostalCode(), actual.getVisitingAddress().getPostalCode());
        assertEquals(expected.getVisitingAddress().getPostOffice(), actual.getVisitingAddress().getPostOffice());
        assertEquals(expected.getPostalAddress().getStreet(), actual.getPostalAddress().getStreet());
        assertEquals(expected.getPostalAddress().getPostalCode(), actual.getPostalAddress().getPostalCode());
        assertEquals(expected.getPostalAddress().getPostOffice(), actual.getPostalAddress().getPostOffice());
    }



}
