package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import fi.helsinki.koulutustarjonta.dto.ApplicationSystemDTO;
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
        }
    }
}
