package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.Requirement;
import fi.helsinki.koulutustarjonta.dto.ApplicationOptionDTO;
import fi.helsinki.koulutustarjonta.dto.I18NDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionModelMapper extends ModelMapper {
    public ApplicationOptionModelMapper(String apiEndpoint) {
        super();
        this.addConverter(new RequirementConverter());
        this.addMappings(new ApplicationOptionMap(apiEndpoint));
    }

    class RequirementConverter extends AbstractConverter<Requirement, I18NDTO> {
        @Override
        protected I18NDTO convert(Requirement source) {
            return map(source.getDescription(), I18NDTO.class);
        }
    }

    class ApplicationOptionMap extends PropertyMap<ApplicationOption, ApplicationOptionDTO> {
        final ApplicationSystemOidConverter applicationSystemOidConverter;
        protected ApplicationOptionMap(String apiEndpoint) {
            super();
            this.applicationSystemOidConverter = new ApplicationSystemOidConverter(apiEndpoint);
        }

        @Override
        protected void configure() {
            using(applicationSystemOidConverter).map().setApplicationSystem(source.getApplicationSystem());

        }
    }

    class ApplicationSystemOidConverter extends AbstractConverter<String, String> {
        final String apiEndpoint;

        ApplicationSystemOidConverter(String apiEndpoint) {
            this.apiEndpoint = apiEndpoint;
        }

        @Override
        protected String convert(String source) {
            return String.format("%s/haku/%s", apiEndpoint, source);
        }
    }
}
