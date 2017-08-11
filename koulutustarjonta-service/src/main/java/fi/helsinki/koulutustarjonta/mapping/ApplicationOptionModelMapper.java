package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.Requirement;
import fi.helsinki.koulutustarjonta.dto.ApplicationOptionDTO;
import fi.helsinki.koulutustarjonta.dto.I18NDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionModelMapper extends ModelMapper {
    public ApplicationOptionModelMapper(String apiEndpoint) {
        super();
        this.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
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
            map().setTranslations(source.getSelectionCriteria().availableTranslations());
            using(applicationSystemOidConverter).map().setApplicationSystem(source.getApplicationSystem());
            using(new UrlConverter()).map(source).setAoFormUrl(null);
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

    class UrlConverter extends AbstractConverter<ApplicationOption, I18NDTO> {
        @Override
        protected I18NDTO convert(ApplicationOption source) {
            boolean systemApplicationForm = source.isAsSystemApplicationForm();
            if (!systemApplicationForm && source.getAsFormUrl() != null) {
                String s = String.format("https://opintopolku.fi/hakuperusteet/ao/%s", source.getOid());
                return new I18NDTO(s,s,s);
            } else if (source.getAsFormUrl() != null && systemApplicationForm) {
                return opintopolkuUrl(source.getApplicationSystem());
            } else {
                return new I18NDTO("", "", "");
            }
        }

        private I18NDTO opintopolkuUrl(String oid) {
            return new I18NDTO(
                    String.format("%s/haku-app/lomake/%s", "https://opintopolku.fi", oid),
                    String.format("%s/haku-app/lomake/%s", "https://studieinfo.fi", oid),
                    String.format("%s/haku-app/lomake/%s", "https://studyinfo.fi", oid)
            );
        }
    }


}
