package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.dto.LearningOpportunityDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityModelMapper extends ModelMapper {
    public LearningOpportunityModelMapper(String apiEndpoint) {
        super();
        this.addMappings(new LearningOpportunityMap(apiEndpoint));
    }

    class LearningOpportunityMap extends PropertyMap<LearningOpportunity, LearningOpportunityDTO> {
        final ApplicationOptionOidConverter applicationOptionOidConverter;
        final OrganizationOidConverter organizationOidConverter;

        protected LearningOpportunityMap(final String apiEndpoint) {
            super();
            this.applicationOptionOidConverter = new ApplicationOptionOidConverter(apiEndpoint);
            this.organizationOidConverter = new OrganizationOidConverter(apiEndpoint);
        }

        @Override
        protected void configure() {
            map().setTranslations(source.getGoals().availableTranslations());
            using(applicationOptionOidConverter).map().setApplicationOptions(source.getApplicationOptions());
            using(organizationOidConverter).map().setProvider(source.getProvider());
        }
    }


    private class ApplicationOptionOidConverter extends AbstractConverter<List<String>, List<String>> {
        final String apiEndpoint;

        ApplicationOptionOidConverter(String apiEndpoint) {
            this.apiEndpoint = apiEndpoint;
        }

        @Override
        protected List<String> convert(List<String> source) {
            return source.stream()
                    .map(ao -> String.format("%s/hakukohde/%s", apiEndpoint, ao))
                    .collect(Collectors.toList());
        }
    }

    private class OrganizationOidConverter extends AbstractConverter<String, String> {
        final String apiEndpoint;

        private OrganizationOidConverter(String apiEndpoint) {
            this.apiEndpoint = apiEndpoint;
        }

        @Override
        protected String convert(String source) {
            return String.format("%s/organisaatio/%s", apiEndpoint, source);
        }
    }
}
