package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.Requirement;
import fi.helsinki.koulutustarjonta.dto.ApplicationOptionDTO;
import fi.helsinki.koulutustarjonta.dto.I18NDTO;
import javafx.application.Application;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionModelMapper extends ModelMapper {
    static final Logger LOG = LoggerFactory.getLogger(ApplicationOptionModelMapper.class);
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
            if(source.getAoFormUrl() != null && source.getAoFormUrl().equals("ataruFormUrl")){
                return ataruTypeUrl(source.getOid());
            } else if (!systemApplicationForm && source.getAsFormUrl() != null) {
                return hakuperusteetUrl(source.getOid());
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

        private I18NDTO hakuperusteetUrl(String oid) {
            return new I18NDTO(
                    String.format("%s/hakuperusteet/ao/%s", "https://opintopolku.fi", oid),
                    String.format("%s/hakuperusteet/ao/%s", "https://studieinfo.fi", oid),
                    String.format("%s/hakuperusteet/ao/%s", "https://studyinfo.fi", oid)
            );
        }

        private I18NDTO ataruTypeUrl(String oid) {
            return new I18NDTO(
                    String.format("https://opintopolku.fi/hakemus/hakukohde/%s?lang=fi", oid),
                    String.format("https://opintopolku.fi/hakemus/hakukohde/%s?lang=sv", oid),
                    String.format("https://opintopolku.fi/hakemus/hakukohde/%s?lang=en", oid)
            );
        }

    }


}
