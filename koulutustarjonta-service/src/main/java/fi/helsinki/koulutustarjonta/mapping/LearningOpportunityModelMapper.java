package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.LOContact;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.dto.LOContactDTO;
import fi.helsinki.koulutustarjonta.dto.LearningOpportunityDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.ArrayList;
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
        final LOContactConverter loContactConverter;

        protected LearningOpportunityMap(final String apiEndpoint) {
            super();
            this.applicationOptionOidsConverter = new ApplicationOptionOidsConverter(apiEndpoint);
            this.organizationOidConverter = new OrganizationOidConverter(apiEndpoint);
            this.learningOpportunityOidsConverter = new LearningOpportunityOidsConverter(apiEndpoint);
            this.loContactConverter = new LOContactConverter();
        }

        @Override
        protected void configure() {
            map().setTranslations(source.getGoals().availableTranslations());

            using(loContactConverter).map(source.getContactInfos()).setLoContacts(null);

            using(applicationOptionOidsConverter).map().setApplicationOptions(source.getApplicationOptions());
            using(organizationOidConverter).map().setProvider(source.getProvider());
            using(learningOpportunityOidsConverter).map().setParents(source.getParents());
            using(learningOpportunityOidsConverter).map().setChildren(source.getChildren());
        }
    }

    /*
    Converter<String, String> toUppercase = new AbstractConverter<String, String>() {
      protected String convert(String source) {
        return source == null ? null : source.toUppercase();
      }
    };

     */
    private class LOContactConverter extends AbstractConverter<List<LOContact>, List<LOContactDTO>> {

        @Override
        protected List<LOContactDTO> convert(List<LOContact> source) {
            if(source == null) {
                return new ArrayList<>();
            }
            return source.stream().filter(e -> e != null).map(c ->
                LOContactDTO.builder()
                    .contactType(c.getContactType())
                    .email(c.getEmail())
                    .languages(c.getLanguages())
                    .title(c.getTitle())
                    .name(c.getName())
                    .phoneNumber(c.getPhoneNumber())
                    .build()
            ).collect(Collectors.toList());
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

    private class OrganizationOidConverter extends AbstractConverter<List<String>, List<String>> {
        final String apiEndpoint;

        private OrganizationOidConverter(String apiEndpoint) {
            this.apiEndpoint = apiEndpoint;
        }

        @Override
        protected List<String> convert(List<String> source) {
            if (source == null || source.isEmpty()) {
                return null;
            }
            else {
                return source.stream()
                        .map(lo -> String.format("%s/organisaatio/%s", apiEndpoint, lo))
                        .collect(Collectors.toList());
            }
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
