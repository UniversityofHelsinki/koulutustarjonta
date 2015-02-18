package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import fi.helsinki.koulutustarjonta.dto.ApplicationSystemDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import static org.modelmapper.Conditions.isNull;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationSystemModelMapper extends ModelMapper {

    public ApplicationSystemModelMapper() {
        super();
        this.addMappings(new ApplicationSystemMap());
    }

    class ApplicationSystemMap extends PropertyMap<ApplicationSystem, ApplicationSystemDTO> {
        @Override
        protected void configure() {
            map().setTranslations(source.getName().availableTranslations());
            using(new UrlConverter()).map(source).setApplicationFormUrl(null);
        }
    }

    class UrlConverter extends AbstractConverter<ApplicationSystem, String> {
        @Override
        protected String convert(ApplicationSystem source) {
            return source.getOpintopolkuFormUrl() != null ? source.getOpintopolkuFormUrl() : source.getApplicationFormUrl();
        }
    }
}
