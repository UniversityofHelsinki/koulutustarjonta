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
        final ApplicationOptionOidsConverter applicationOptionOidsConverter;
        final OrganizationOidConverter organizationOidConverter;
        final LearningOpportunityOidsConverter learningOpportunityOidsConverter;

        protected LearningOpportunityMap(final String apiEndpoint) {
            super();
            this.applicationOptionOidsConverter = new ApplicationOptionOidsConverter(apiEndpoint);
            this.organizationOidConverter = new OrganizationOidConverter(apiEndpoint);
            this.learningOpportunityOidsConverter = new LearningOpportunityOidsConverter(apiEndpoint);
        }

        @Override
        protected void configure() {
            map().setTranslations(source.getGoals().availableTranslations());
            using(applicationOptionOidsConverter).map().setApplicationOptions(source.getApplicationOptions());
            using(organizationOidConverter).map().setProvider(source.getProvider());
            using(learningOpportunityOidsConverter).map().setParents(source.getParents());
            using(learningOpportunityOidsConverter).map().setChildren(source.getChildren());
        }
    }

    private class ApplicationOptionOidsConverter extends AbstractConverter<List<String>, List<String>> {
        final String apiEndpoint;

        ApplicationOptionOidsConverter(String apiEndpoint) {
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

    private class LearningOpportunityOidsConverter extends AbstractConverter<List<String>, List<String>> {
        final String apiEndpoint;

        private LearningOpportunityOidsConverter(String apiEndpoint) {
            this.apiEndpoint = apiEndpoint;
        }

        @Override
        protected List<String> convert(List<String> source) {
            if (source == null || source.isEmpty()) {
                return null;
            }
            else {
                return source.stream()
                        .map(ao -> String.format("%s/koulutus/%s", apiEndpoint, ao))
                        .collect(Collectors.toList());
            }
        }
    }
}
