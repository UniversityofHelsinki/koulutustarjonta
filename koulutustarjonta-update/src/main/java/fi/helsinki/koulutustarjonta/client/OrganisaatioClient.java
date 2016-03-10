package fi.helsinki.koulutustarjonta.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import fi.helsinki.koulutustarjonta.client.converter.OrganizationConverter;
import fi.helsinki.koulutustarjonta.domain.Organization;
import fi.helsinki.koulutustarjonta.exception.DataUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class OrganisaatioClient {

    private static final Logger LOG = LoggerFactory.getLogger(OrganisaatioClient.class);

    private final List<String> organizationOidIgnore;
    private final WebResource organizationResource;
    private final OrganizationConverter organizationConverter;

    public OrganisaatioClient(WebResource organizationResource, KoodistoClient koodistoClient, List<String> organizationOidIgnore) {
        this.organizationResource = organizationResource;
        this.organizationConverter = new OrganizationConverter(koodistoClient);
        this.organizationOidIgnore = organizationOidIgnore;
    }

    public Organization getOrganization(String oid) throws DataUpdateException{
        LOG.debug(String.format("Fetching organization with oid %s", oid));
        JsonNode node = organizationResource.path(oid)
                .get(new GenericType<JsonNode>() {});

        return organizationConverter.convert(node);
    }

    public List<String> resolveFacultyOids(String parentOid) {
        JsonNode oids = organizationResource.path(parentOid).path("childoids")
                .get(new GenericType<JsonNode>() {}).get("oids");
        LOG.debug(String.format("Found %d organization oids", oids.size()));
        List<String> collect = Lists.newArrayList(oids).parallelStream()
                .map(item -> item.textValue())
                .filter(orgOid -> organizationOidIgnore.stream()
                        .anyMatch(ignoredOrgOid -> !ignoredOrgOid.equalsIgnoreCase(orgOid)))
                .collect(toList());
        LOG.debug(String.format("After filtering %d oids", collect.size()));
        return collect;
    }

}
