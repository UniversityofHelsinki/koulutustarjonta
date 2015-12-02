package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import fi.helsinki.koulutustarjonta.dto.ApplicationSystemDTO;
import fi.helsinki.koulutustarjonta.dto.I18NDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

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
            using(new UrlConverter()).map(source).setFormUrl(null);
        }
    }

    class UrlConverter extends AbstractConverter<ApplicationSystem, I18NDTO> {
        @Override
        protected I18NDTO convert(ApplicationSystem source) {
            String s = source.getFormUrl();

            if ( s != null && s == "system" ) {
                return opintopolkuUrl(source.getOid());
            }
            else if (s != null) {
                return new I18NDTO(s, s, s);
            }
            else {
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
